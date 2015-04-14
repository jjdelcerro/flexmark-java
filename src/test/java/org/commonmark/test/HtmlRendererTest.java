package org.commonmark.test;

import static org.junit.Assert.*;

import org.commonmark.HtmlRenderer;
import org.commonmark.Parser;
import org.commonmark.node.Node;
import org.junit.Test;

public class HtmlRendererTest {

	@Test
	public void foo() {
		Parser parser = Parser.builder().build();
		Node node = parser.parse("foo *bar*");

		HtmlRenderer renderer = HtmlRenderer.builder().build();
		String result = renderer.render(node);

		assertEquals("<p>foo <em>bar</em></p>\n", result);
	}

}