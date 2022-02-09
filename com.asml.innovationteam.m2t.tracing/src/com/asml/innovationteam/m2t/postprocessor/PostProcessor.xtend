package com.asml.innovationteam.m2t.postprocessor

import java.util.HashMap
import java.util.Map
import java.util.regex.Pattern
import org.eclipse.xtend.lib.annotations.Accessors

class PostProcessor {
	static class MetaLocation {
		new(int k) {
			key = k;
		}

		@Accessors(PUBLIC_GETTER) int key;
		@Accessors(PUBLIC_GETTER, PUBLIC_SETTER) int startLine;
		@Accessors(PUBLIC_GETTER, PUBLIC_SETTER) int startCol;
		@Accessors(PUBLIC_GETTER, PUBLIC_SETTER) int endLine;
		@Accessors(PUBLIC_GETTER, PUBLIC_SETTER) int endCol;
	}

	Map<Integer, MetaLocation> metaData = new HashMap

	static Pattern metaBeginRx = Pattern.compile('(\\[(\\d+)\\[)(.*)')
	static Pattern metaEndRx = Pattern.compile('(\\](\\d+)\\])(.*)')
	static Pattern eolRx = Pattern.compile('(\\n\\r|\\r\\n|\\r|\\n)')
	static Pattern lineRx = Pattern.compile('([^\\n\\r]*)' + eolRx + '?(.*)',
		Pattern.MULTILINE.bitwiseOr(Pattern.DOTALL))

	def String extractEssentialText(String src) {
		var buffer = src;
		var lineCounter = 1;
		val result = new StringBuffer
		while (buffer.length > 0) {
			val m = lineRx.matcher(buffer)
			if (m.matches) {
				val line = m.group(1)
				val eol = m.group(2)
				val rest = m.group(3)

				val extractedLine = extractEssentialTextFromLine(line, lineCounter)

				result.append(extractedLine).append(eol)
				buffer = rest
			} else {
				result.append(buffer)
				buffer = ''
			}
			lineCounter++
		}
		result.toString
	}

	def String extractEssentialTextFromLine(String line, int lineNr) {
		var buffer = line
		var result = new StringBuffer
		var colNr = 0
		while (buffer.length > 0) {
			switch (buffer) {
				case (handleOpenMeta(buffer)): {
					val openBrackPos = buffer.indexOf('[')
					result.append(buffer.subSequence(0, openBrackPos))
					buffer = buffer.substring(openBrackPos)
					colNr += openBrackPos
					val m = metaBeginRx.matcher(buffer)
					if (m.matches) {
						val meta = m.group(1)
						val metaIdx = Integer.parseInt(m.group(2))
						val restOfLine = m.group(3)
						val md = new MetaLocation(metaIdx)
						md.startLine = lineNr
						md.startCol = colNr
						metaData.put(metaIdx, md)
						buffer = restOfLine
					} else {
						result.append('[')
						buffer = buffer.substring(1)
						colNr += 1
					}
				}
				case (handleCloseMeta(buffer)): {
					val closeBrackPos = buffer.indexOf(']')
					result.append(buffer.subSequence(0, closeBrackPos))
					buffer = buffer.substring(closeBrackPos)
					colNr += closeBrackPos
					val m = metaEndRx.matcher(buffer)
					if (m.matches) {
						val meta = m.group(1)
						val metaIdx = Integer.parseInt(m.group(2))
						val restOfLine = m.group(3)
						val md = metaData.get(metaIdx)
						md.endLine = lineNr
						md.endCol = colNr

						buffer = restOfLine
					} else {
						result.append(']')
						buffer = buffer.substring(1)
						colNr += 1
					}
				}
				default: {
					result.append(buffer)
					buffer = ''
				}
			}

		}
		result.toString()
	}

	def boolean handleOpenMeta(String buffer) {
		val openBrackPos = buffer.indexOf('[')
		val closeBrackPos = buffer.indexOf(']')
		return (openBrackPos >= 0) && (closeBrackPos < 0 || closeBrackPos > openBrackPos)
	}

	def boolean handleCloseMeta(String buffer) {
		val openBrackPos = buffer.indexOf('[')
		val closeBrackPos = buffer.indexOf(']')
		return (closeBrackPos >= 0) && (openBrackPos < 0 || closeBrackPos < openBrackPos)
	}

	def String metaAsString() {
		metaData.entrySet.sortBy[mde|mde.key].map[mde|mde.value].map [ md |
			'''<«md.key», «md.startLine», «md.startCol», «md.endLine», «md.endCol»>'''
		].join('\n')
	}

	def static void main(String[] args) {
		val testString = '''
			void [0[Foo_42]0] {
				[1[// This is [2[Foo_43]2]'s body
				skip();
				return;
				]1]
			}
		'''

		val pp = new PostProcessor
		val cleanString = pp.extractEssentialText(testString)
		System.out.println(cleanString)
		System.err.println(pp.metaAsString + "\n")
		System.err.println(testString)
	}
}
