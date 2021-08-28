package com.mao.cal;

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
	private boolean state;

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

	public void setState(boolean state)
	{
		this.state = state;
	}

	public boolean isState()
	{
		return state;
	}

	public void init(){
		
		Counts count = new Counts(new int[row][column]);
		counts = count.getCounts();
		setN(count.getN());
		setState(true);

		for (int i=0;i < row;i++)
		{
			for (int j=0;j < column;j++)
			{
				final MyTextView rb = (MyTextView)LayoutInflater.from(this.getContext()).inflate(R.layout.view, null, false);
				this.addView(rb);
				rb.setM(i);
				rb.setN(j);

				rb.setOnClickListener(new View.OnClickListener()
					{
						private boolean flag = true;

						public void onClick(View view) 
						{
							int x = counts[rb.getM()][rb.getN()];

							if (state)
							{
								if (!MainActivity.flag)
								{
									if (x == -1)
									{
										rb.setBackgroundResource(R.drawable.lei);
										//update();
										setState(false);

										Intent intent = new Intent();
										intent.setClass(MyViewGroup.this.getContext(), DialogActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										MyViewGroup.this.getContext().startActivity(intent);
									}
									else
									{
										rb.setText("" + x);
										rb.setTextColor(Color.parseColor("#FF0000FF"));
										rb.setBackgroundColor(Color.parseColor("#5000F0F0"));

										if (--n == 0)
										{
											//恭喜过关
											rb.setText("过关");
											rb.setTextColor(Color.parseColor("#FFFF0000"));
										}

									}
									rb.setOnClickListener(new View.OnClickListener(){public void onClick(View view)
											{}});

								}
								else
								{
									if (flag)
									{
										rb.setBackgroundResource(R.drawable.flag);
										flag = false;
									}
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
	
	public void update()
	{
		removeAllViews();
		init();
		invalidate();
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
