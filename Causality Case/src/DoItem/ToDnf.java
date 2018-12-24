package DoItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


/**
 * ����Python�����ת����DNF������
 * 
 * @author misen
 *
 */
public class ToDnf {

    public static String toDnf(String expr) {
        expr = encode(expr);
        String result = callPython(expr);
        System.out.println(decode(result));
        return decode(result);
//        return transform(decode(result ));
    }
    
    /**
     * python�Ŀ⺯��֧�� "~"��ʽ��not, ��֧��"!"��not, ͬʱ"-"����������, ����Ϊ�������еļ���, ���Զ�������б���ӳ��, �����Ǹ������
     * @param expr
     * @return
     */

    private static String encode(String expr) {
        return expr.replaceAll("!", "~").replaceAll("-", "_");
    }

    private static String decode(String expr) {
//    	System.out.println(expr);
        return expr.replaceAll("_", "-").replaceAll(" ", "").replaceAll("~", "!");
    }
    
    /**
     * ��exprд��tmp�ﴫ�ݱ��ʽ
     * @param expr
     * @return
     */

    private static String callPython(String expr) {
        String result = "";
        try {
            FileWriter writer = new FileWriter("./tmp", true);	//�ڸ����ļ���������¹��� FileWriter ����true��ʾ��ԭ�е��ļ���������,false��ʾ���ԭ����������д�롣
            writer.append(expr);	//��ԭ�����ݵĻ�����׷��
            writer.close();
            String[] args=new String[]{"python","./to_dnf.py"};
            Process process = Runtime.getRuntime().exec(args);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();
            input.close();
            ir.close();

            File file = new File("./tmp");
            file.delete();
        } catch (IOException e) {
        }
        return result;
    }

    /**
     * S���ʽת������׺��ʽ��DNF
     * @param expr
     * @return
     */
    private static String transform(String expr) {
        if (expr.startsWith("Or")) {
            String dnf = "";

            expr = expr.substring(3, expr.length() - 1);
            int par = 0;
            int start = 0;
            for (int i = 0; i < expr.length(); i++) {
                if (par == 0 && expr.charAt(i) == ',') {
                    String term = expr.substring(start, i);
                    dnf += "|" + "(" + term(term) + ")";
                    start = i + 1;
                }
                else if (i == expr.length() - 1) {
                    String term = expr.substring(start);
                    dnf += "|" + "(" + term(term) + ")";
                }
                else if (expr.charAt(i) == '(') {
                    par++;
                }
                else if (expr.charAt(i) == ')') {
                    par--;
                }
            }

            return dnf.substring(1);
        }
        else if (expr.startsWith("And")) {
            return term(expr);
        }
        else {
            throw new Error("error!");
        }
    }

    private static String term(String term) {
        if (term.startsWith("And")) {
            term = term.substring(4, term.length() - 1);
            String[] literals = term.split(",");

            term = "";
            for (String l : literals) {
                if (l.contains("Not")) {
                    term += "&" + l.substring(4, l.length() - 1);
                }
                else {
                    term += "&" + l;
                }
            }

            return term.substring(1);
        }
        else {
            return term;
        }
    }

}

