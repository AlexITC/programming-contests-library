package com.alex.collection;

public class PQueue {
	int [] dq;
	int [] q;
	int dq_st, dq_len, q_st, q_len;

	public PQueue(int N)	{
		dq = new int[N];
		q = new int[N];
		dq_st = dq_len = q_st = q_len = 0;
	}
	void add(int x) {
		while ( dq_len != 0 && dq[ dq_st + dq_len - 1 ] > x)	{
			dq_len--;
		}
		
		dq[dq_st + dq_len++] = x;
		q[q_st + q_len++] = x;
	}

	int getMin() {
		return dq[dq_st];
	}

	void poll() {
		int min = q[q_st++];
		q_len--;
		if (min == dq[dq_st] )	{
			dq_st++;
			dq_len--;
		}
	}
}

