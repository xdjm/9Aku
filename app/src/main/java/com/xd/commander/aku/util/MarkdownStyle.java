package com.xd.commander.aku.util;

import br.tiagohm.markdownview.css.styles.Bootstrap;

/**
 * Created by Administrator on 2017/4/24.
 */

public class MarkdownStyle extends Bootstrap {
    public MarkdownStyle() {
        this.addRule("body", "line-height: 1.3","padding: 5px","color: #e8f5e9","background-color: #3f3f3f");
        this.addRule("h1", "font-size: 20px","color: #ff8a80");
        this.addRule("h2", "font-size: 18px","color: #ff8a80");
        this.addRule("h3", "font-size: 16px","color: #ff8a80");
        this.addRule("h4", "font-size: 14px","color: #ff8a80");
        this.addRule("h5", "font-size: 12px");
        this.addRule("h6", "font-size: 10px");
        this.addRule("pre", "position: relative", "padding: 14px 10px", "border: 0", "border-radius: 3px", "background-color: #282828","color: #33567a");
        this.addRule("pre code", "position: relative", "line-height: 1.2", "background-color: transparent");
        this.addRule("table tr:nth-child(2n)", "background-color: #f6f8fa");
        this.addRule("table th", "padding: 6px 13px", "border: 1px solid #dfe2e5");
        this.addRule("table td", "padding: 6px 13px", "border: 1px solid #dfe2e5");
        this.addRule("kbd", "color: #444d56", "font-family: Consolas, \"Liberation Mono\", Menlo, Courier, monospace", "background-color: #fcfcfc", "border: solid 1px #c6cbd1", "border-bottom-color: #959da5", "border-radius: 3px", "box-shadow: inset 0 -1px 0 #959da5");
        this.addRule("pre[language]::before", "content: attr(language)", "position: absolute", "top: 0", "right: 5px", "padding: 2px 1px", "text-transform: uppercase", "color: #666", "font-size: 8.5px");
        this.addRule("pre:not([language])", "padding: 6px 10px");
        this.addRule(".footnotes li p:last-of-type", "display: inline");
        this.addRule(".yt-player", "border: 2px solid #c51109");
        this.addRule(".hljs-comment", "color: #8e908c");
        this.addRule(".hljs-quote", "color: #8e908c");
        this.addRule(".hljs-variable", "color: #c82829");
        this.addRule(".hljs-template-variable", "color: #c82829");
        this.addRule(".hljs-tag", "color: #c82829");
        this.addRule(".hljs-name", "color: #c82829");
        this.addRule(".hljs-selector-id", "color: #c82829");
        this.addRule(".hljs-selector-class", "color: #c82829");
        this.addRule(".hljs-regexp", "color: #c82829");
        this.addRule(".hljs-deletion", "color: #c82829");
        this.addRule(".hljs-number", "color: #f5871f");
        this.addRule(".hljs-built_in", "color: #f5871f");
        this.addRule(".hljs-builtin-name", "color: #f5871f");
        this.addRule(".hljs-literal", "color: #f5871f");
        this.addRule(".hljs-type", "color: #f5871f");
        this.addRule(".hljs-params", "color: #f5871f");
        this.addRule(".hljs-meta", "color: #f5871f");
        this.addRule(".hljs-link", "color: #f5871f");
        this.addRule(".hljs-attribute", "color: #eab700");
        this.addRule(".hljs-string", "color: #718c00");
        this.addRule(".hljs-symbol", "color: #718c00");
        this.addRule(".hljs-bullet", "color: #718c00");
        this.addRule(".hljs-addition", "color: #718c00");
        this.addRule(".hljs-title", "color: #4271ae");
        this.addRule(".hljs-section", "color: #4271ae");
        this.addRule(".hljs-keyword", "color: #8959a8");
        this.addRule(".hljs-selector-tag", "color: #8959a8");
        this.addRule("li", "color:#fde77c");
    }
}
