package sample.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tutorial.example2.service.DictionaryService;

@SuppressWarnings("serial")
public class HelloWorldServlet extends HttpServlet {
	private DictionaryService dictionaryService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.write(getHeader());
		writer.write(getDictionaryResult(request));
		writer.write(getForm());
		writer.write(getFooter());
	}

	private String getForm() {
		String form = "<p><form method=\"GET\">" +
				"<input type=\"text\" name=\"checkword\"/><input type=\"submit\"></p>";
		return form;
	}

	private String getDictionaryResult(HttpServletRequest request) {
		String wordToCheck = request.getParameter("checkword");
		String dictionaryResult = "";
		if (wordToCheck != null && !"".equals(wordToCheck)) {
			dictionaryResult = checkTheWord(wordToCheck);
		}
		return dictionaryResult;
	}

	private String getHeader() {
		String header = "<html><head><title>Hello dictionary!!</title><body>";
		return header;
	}

	private String getFooter() {
		String footer = "</body></html>";
		return footer;
	}

	private String checkTheWord(String checkWord) {
		String returnMessage = "";
		if (dictionaryService == null) {
			returnMessage =  "Oops, temporarily no dictionary available";
		} else {
			returnMessage = "the word <b>" + checkWord + "</b> is : ";
			if (dictionaryService.checkWord(checkWord)) {
				returnMessage += "valid ";
			} else {
				returnMessage += "not valid ";
			}
			returnMessage += "in the current dictionary";

		}

		return returnMessage;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}
