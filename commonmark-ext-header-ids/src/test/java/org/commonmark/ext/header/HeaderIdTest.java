package org.commonmark.ext.headerids;

import org.commonmark.html.HtmlRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.test.RenderingTestCase;
import org.junit.Before;
import org.junit.Test;

public class HeaderIdTest extends RenderingTestCase {

    private static final Parser PARSER = Parser.builder().build();
    private static HtmlRenderer RENDERER = HtmlRenderer.builder().attributeProvider(HeaderIdAttributeProvider.create()).build();

    @Before
    public void resetHeader() {
        RENDERER = HtmlRenderer.builder()
                .attributeProvider(HeaderIdAttributeProvider.create())
                .build();
    }

    @Test
    public void baseCaseSingleHeader() {
        assertRendering("# Heading here\n",
                "<h1 id=\"heading-here\">Heading here</h1>\n");
    }

    @Test
    public void singleHeaderWithCodeBlock() {
        assertRendering("Hi there\n# Heading `here`\n",
                "<p>Hi there</p>\n<h1 id=\"heading-here\">Heading <code>here</code></h1>\n");
    }

    @Test
    public void duplicateHeadersMakeUniqueIds() {
        assertRendering("# Heading here\n# Heading here",
                "<h1 id=\"heading-here\">Heading here</h1>\n<h1 id=\"heading-here1\">Heading here</h1>\n");
    }

    @Test
    public void duplicateHeadersOnceDissalowedCharactersMakeUniqueIds() {
        assertRendering("# Hi there\n" +
                "# ∂Hi å∂There˚∂ˆ´¨", "<h1 id=\"hi-there\">Hi there</h1>\n" +
                "<h1 id=\"hi-there1\">∂Hi å∂There˚∂ˆ´¨</h1>\n");
    }

    @Test
    public void testCaseIsIgnoredWhenComparingIds() {
        assertRendering("# HEADING here\n" +
                        "# heading here",
                "<h1 id=\"heading-here\">HEADING here</h1>\n" +
                        "<h1 id=\"heading-here1\">heading here</h1>\n");
    }

    @Test
    public void testNestedBlocks() {
        assertRendering("## `h` `e` **l** *l* o",
                "<h2 id=\"h-e-l-l-o\"><code>h</code> <code>e</code> <strong>l</strong> <em>l</em> o</h2>");
    }

    @Override
    protected String render(String source) {
        return RENDERER.render(PARSER.parse(source));
    }
}
