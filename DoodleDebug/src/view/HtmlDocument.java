package view;

import html_generator.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class HtmlDocument {

	private Tag header;
	private String doctype;
	private Tag body;

	public HtmlDocument() {
		this.header = makeHeader();
		this.doctype = makeDoctype();
		this.body = new Tag("body");
	}

	private String makeDoctype() {
		return "<!DOCTYPE html>\n";
	}

	@SuppressWarnings("unchecked")
	private Tag makeHeader() {
		Tag header = new Tag("head");
		Tag title = new Tag("title");
		title.add("DoodleDebug");
		header.add(title);
		header.add(makeStyle());
		return header;
	}

	@SuppressWarnings("unchecked")
	private Tag makeStyle() {
		Tag style = new Tag("style");
		style.add(makeStyleSheet());
		return style;
	}

	private String makeStyleSheet() {
		// load from file "style.css"
		String sep = File.separator;
		return readFile(this.getClass().getResource("style.css"));
	}

	private String readFile(URL url) {
		String result = "";
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = "";
			while (inputLine != null) {
				result += inputLine+"\n";
				inputLine = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	public void setBody(Tag body) {
		this.body = body;
	}

	public String toString() {
		return "" + doctype + header + body;
	}
}
