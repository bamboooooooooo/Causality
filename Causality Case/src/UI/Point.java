package UI;

import java.awt.Color;

public class Point {
	int x, y;// ����λ��
	Color col;// ��ͼѡ�����ɫ
	int type;// ��ͼѡ������ͼ�Σ�״̬0/��ϵ����1��2��3...��
	int boarder;// �������
	public Point(int x, int y, Color col, int type) {
		this.x = x;
		this.y = y;
		this.col = col;//Ĭ��
		this.type = type;
		this.boarder = boarder;//Ĭ��
	}

}
