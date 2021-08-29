package com.mao.lei;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.content.res.*;
import android.graphics.*;
import android.support.v7.app.*;

public class MyViewGroup extends ViewGroup
{
	private int column;
	private int margin;
	private int row;
	private int width;
	private int height;
	private int childCount;
	private int[][] counts;
	private int n;
	private boolean end;

	public MyViewGroup(Context context)
	{
		super(context);
	}

	public MyViewGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		//获取xml配置
		TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MyViewGroup);
		column = ta.getInteger(R.styleable.MyViewGroup_column,10);
		margin = ta.getInteger(R.styleable.MyViewGroup_margin,6);
		row = ta.getInteger(R.styleable.MyViewGroup_row,19);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//获取最大宽度和
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		width = (maxWidth-margin)/column-margin;
		height = width;
		int maxHeight = (height+margin)*(int)Math.ceil(childCount*1.0/column)+margin;

		for(int i=0;i<childCount;i++)
		{
			final View child = getChildAt(i);
			int wm = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
			int hm = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
			//设置子类所需宽度和高度
			child.measure(wm,hm);
		}

		// 设置容器所需的宽度和高度
		setMeasuredDimension(maxWidth, maxHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		//super.onLayout(changed, l, t, r, b);

		for (int i = 0; i < childCount; i++)
		{
			View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE)
			{
				child.layout((i%column)*(width+margin)+margin, (i/column)*(height+margin)+margin, (i%column+1)*(width+margin), (i/column+1)*(height+margin));	
			}
		}
	}

	public void init(){

		Counts count = new Counts(new int[row][column]);
		counts = count.getCounts();
		setN(count.getN());
		setEnd(false);

		for (int i=0;i < row;i++)
		{
			for (int j=0;j < column;j++)
			{
				final MyTextView rb = (MyTextView)LayoutInflater.from(this.getContext()).inflate(R.layout.view, null, false);
				this.addView(rb);
				rb.setNum(counts[i][j]);
				rb.setM(i);
				rb.setN(j);

				rb.setOnClickListener(new View.OnClickListener()
					{
						private boolean flag = true;

						public void onClick(View view) 
						{
							int x = counts[rb.getM()][rb.getN()];

							//是否结束
							if (!isEnd())
							{
								//翻开方块
								if (!MainActivity.flag)
								{
									//判断是雷
									if (x==-1)
									{
										rb.setBackgroundResource(R.drawable.lei);
										//点击到雷结束
										setEnd(true);
										//对话框
										Intent intent = new Intent();
										intent.setClass(MyViewGroup.this.getContext(), DialogActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										MyViewGroup.this.getContext().startActivity(intent);
									}else{
										open(rb);
									}
									//旗子
								}else{
									//放置旗子
									if (flag)
									{
										rb.setBackgroundResource(R.drawable.flag);
										flag = false;
									}
									//取消旗子
									else
									{
										rb.setBackgroundColor(Color.parseColor("#F0AAAAAA"));
										flag = true;
									}	
								}
							} 
						}
					});
			}

		}

		childCount = getChildCount();
	}

	public void open(MyTextView mtv)
	{
		int num = mtv.getNum();

		mtv.setTextColor(Color.parseColor("#FF0000FF"));
		mtv.setBackgroundColor(Color.parseColor("#3000F0F0"));
		mtv.setOnClickListener(null);
		mtv.setOpened(true);

		if (mtv.getNum() == 0)
		{
			mtv.setText("");
			discover(mtv);
		}else{
			mtv.setText(num+"");
		}

		if (--n==0)
		{
			//恭喜过关
			mtv.setText("胜");
			mtv.setTextColor(Color.parseColor("#FFFF0000"));
		}
	}
	
	public void discover(MyTextView mv)
	{
		int i = mv.getM();
		int j = mv.getN();	
		
		if(i-1>=0&&j-1>=0)
		{
			MyTextView t = (MyTextView)getChildAt((i-1)*column+j-1);
			if(!t.isOpened())open(t);
		}

		if(i-1>=0)
		{
			MyTextView t = (MyTextView)getChildAt((i-1)*column+j);
			if(!t.isOpened())open(t);
		}

		if(i-1>=0&&j+1<column)
		{
			MyTextView t = (MyTextView)getChildAt((i-1)*column+j+1);
			if(!t.isOpened())open(t);
		}
		
		if(j-1>=0)
		{
			MyTextView t = (MyTextView)getChildAt(i*column+j-1);
			if(!t.isOpened())open(t);
		}
	
		if(j+1<column)
		{
			MyTextView t = (MyTextView)getChildAt(i*column+j+1);
			if(!t.isOpened())open(t);
		}

		if(i+1<row&&j-1>=0)
		{
			MyTextView t = (MyTextView)getChildAt((i+1)*column+j-1);
			if(!t.isOpened())open(t);
		}
		
		if(i+1<row)
		{
			MyTextView t = (MyTextView)getChildAt((i+1)*column+j);
			if(!t.isOpened())open(t);
		}
	
		if(i+1<row&&j+1<column)
		{
			MyTextView t = (MyTextView)getChildAt((i+1)*column+j+1);
			if(!t.isOpened())open(t);
		}
	}
	
	public void update()
	{
		removeAllViews();
		init();
		invalidate();
	}

	public void setEnd(boolean end)
	{
		this.end = end;
	}

	public boolean isEnd()
	{
		return end;
	}
	
	public void setN(int n)
	{
		this.n = n;
	}

	public int getN()
	{
		return n;
	}
	
}
