package UI;

import java.awt.Color;

public class Point {
	int x, y;// 鼠标的位置
	Color col;// 画图选择的颜色
	int type;// 画图选择画哪种图形（状态0/关系类型1、2、3...）
	int boarder;// 线条宽度
	public Point(int x, int y, Color col, int type) {
		this.x = x;
		this.y = y;
		this.col = col;//默认
		this.type = type;
		this.boarder = boarder;//默认
	}

}
