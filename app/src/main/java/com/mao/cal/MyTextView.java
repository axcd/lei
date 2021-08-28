package com.mao.cal;

import android.widget.*;
import android.content.*;
import android.util.*;

public class MyTextView extends TextView
{
	private int num;
	private int m;
	private int n;
	
	public MyTextView(Context context)
	{
		super(context);
	}
	
	public MyTextView(Context context,AttributeSet attr)
	{
		super(context,attr);
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getNum()
	{
		return num;
	}

	public void setM(int m)
	{
		this.m = m;
	}

	public int getM()
	{
		return m;
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
