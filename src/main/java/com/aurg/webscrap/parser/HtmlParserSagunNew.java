package com.aurg.webscrap.parser;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.aurg.webscrap.data.DataSagunInput;
import com.aurg.webscrap.data.DataSagunOutput;
import com.aurg.webscrap.data.MyInt;

public class HtmlParserSagunNew extends HtmlParser {

	protected static Logger logger = LoggerFactory.getLogger(HtmlParserSagunNew.class);

	static public boolean doParsing(int tab, DataSagunInput input, String html, DataSagunOutput result) {
		// for test - ��ü HTML ���
		// System.out.println(------------------------------------------------------------------);
		// System.out.println( html );

		if (tab == 1)
			return parseTab1(input, html, result);
		if (tab == 2)
			return parseTab2(input, html, result);

		return false;
	}

	static boolean parseTab1(DataSagunInput input, String html, DataSagunOutput result) {
		int pos;
		String tempStr;

		// �Է� ���������� �Ǻ��Ѵ�.
		if (html.indexOf("����� �������� �ʽ��ϴ�") >= 0) {
			result.fail_reason = "����� �������� �ʽ��ϴ�.��������� Ȯ���ϼ���.";
			return false;
		}

		Document doc = Jsoup.parse(html);

		// ��ǹ�ȣ
		Element eleContent = doc.select("div.pop_title").first();
		String popTitle = eleContent.text();
		
//		logger.debug("popTitle_______{}", popTitle);
//		logger.debug("popTitle_index_______{}", popTitle.substring(popTitle.indexOf(" : ")+3));

		// �������� ��� ��ǹ�ȣ ������ - ��õ������� 2013��ȸ45918
		// ����� �����ؾ� �ϴ� ��ǹ�ȣ ������ - ��õ�������2013��ȸ045918-03
		// �׷��� ������ ������ �ʿ��ϴ�.
		// 1. ��ǹ�ȣ�� ��´�. > ��õ������� 2013��ȸ45918
		tempStr = popTitle.substring(popTitle.indexOf(" : ")+3);
		
		// System.out.println(" 1:[" + tempStr+"]");
		// 2. ����� �κи� �߶󳽴�. > 2013��ȸ45918
		tempStr = tempStr.substring(tempStr.indexOf(" ") + 1);
		// System.out.println(" 2:[" + tempStr +"]");
		// 3. ��ǹ�ȣ�� 0�� ���̱� ���ؼ�, �� 6�ڸ�(2013��ȸ)���� �ڸ���, ��ǹ�ȣ�� lpas �����ڵ带 ���δ�.
		result.key = tempStr.substring(0, 6) + input.getSa_serial() + "-" + input.getBub_lpas_cd();
		// System.out.println(" 3:[" + result.key+"]");

		// ���Ǻ�
		pos = html.indexOf("���Ǻ�");
		if (pos >= 0) {
			result.JaePanBu = cut_value_in_nextBlock(html, "&nbsp;", new MyInt(
					pos), "</td");
		}

		// ������
		pos = html.indexOf("������");
		if (pos >= 0) {
			result.JeobSuDate = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		// ä����
		pos = html.indexOf(">ä����");
		if (pos >= 0) {
			result.ChaeMuJa = cut_value_in_nextBlock(html, "&nbsp;", new MyInt(
					pos), "</td");
		}

		// ���ð�����
		pos = html.indexOf("���ð�����");
		if (pos >= 0) {
			result.GaeSiDate = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		// ������ȹ�ΰ���
		pos = html.indexOf("������ȹ�ΰ���");
		if (pos >= 0) {
			result.InGaDate = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		// ��å������
		pos = html.indexOf("��å������");
		if (pos >= 0) {
			result.MyunChekDate = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		// ��������������
		pos = html.indexOf("��������������");
		if (pos >= 0) {
			result.PageDate = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		// �������
		pos = html.indexOf("�������");
		if (pos >= 0) {
			result.JongKukResult = cut_value_in_nextBlock(html, "&nbsp;",
					new MyInt(pos), "</td").replaceAll("\\.", "");
		}

		return true;
	}

	static boolean parseTab2(DataSagunInput input, String html,
			DataSagunOutput result) {
		int pos;

		logger.info("called");

		// ��������
		pos = html.indexOf("�ѱ���Ƽ���࿡�� �������(�޿��ҵ���) �۴�");
		if (pos >= 0) {
			result.KumGiKyulJung = cut_value_in_nextBlock(html, "#CC6600\">",
					new MyInt(pos), "<").replaceAll("\\.", "");
		}

		// ���ð���������
		pos = html.indexOf("�ѱ���Ƽ���࿡�� ���ð���������/ä���ڸ��/������ȹ�� �۴�");
		if (pos >= 0) {
			result.GaesiKyulJung = cut_value_in_nextBlock(html, "#CC6600\">",
					new MyInt(pos), "<").replaceAll("\\.", "");
		}

		return true;
	}
}
