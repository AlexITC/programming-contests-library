package com.alex.math;

public class FFT {

    final int MAX = 1 << 15;	// N <= 10000
    private double [][] a = new double [MAX][2];
    private double [][] b = new double [MAX][2];
    private int [] rev = new int [MAX << 1];
    private int [] ans = new int [MAX << 1];
    
    
    public String fastMultiplication(String x, String y)    {
        
        int max = Math.max( x.length(), y.length() );
        int total = 1;
        while   ( (total <<= 1) < max );
        total <<= 1;
        
    //    System.out.println("len: " + total);
        
        readPolynom(x, a, total);
        readPolynom(y, b, total);
        
        fft(a, total, false);
        fft(b, total, false);
        for (int i = 0; i < total; i++) {
            a[i] = times( a[i], b[i] );
        }
        
        fft(a, total, true);
        for (int i = 0; i < total; i++) {
            ans[i] = (int) Math.floor( a[i][0] + 0.5 );
        }
        
        int go = 0;
        for (int i = 0; i < total; i++) {
            ans[i] += go;
            go = ans[i] / 10;
            ans[i] %= 10;
        }
        
        // trim zeros
        int ans_ind = total - 1;
        while (ans_ind > 0 && ans[ans_ind] == 0) {
            ans_ind--;
        }
        
        // convert answer to string
        StringBuilder sb = new StringBuilder();
        for (int i = ans_ind; i >= 0; i--) {
            sb.append( ans[i] );
        }
        return	sb.toString();
    }

    private void readPolynom(String s, double [][] p, int len)  {
        int N = s.length();
        
        int pos = 0;
        for (int i = N - 1; i >= 0; i--) {
            p[pos][0] = s.charAt(i) - '0';
            p[pos][1] = 0;
            pos++;
        }
        while	( pos < len )	{
        	p[pos][0] = p[pos][1] = 0;
        	pos++;
        }
    }
    
    
    private void fft(double [][] p, int N, boolean invert) {
        int dig = 0;
        while ((1 << dig) < N)  dig++;
        
        for (int i = 0; i < N; i++) {
            rev[i] = (rev[i >> 1] >> 1) | ((i & 1) << (dig - 1));
            if (rev[i] > i && rev[i] < p.length) {
                // swap(p[i], p[rev[i]]);
            	double [] aux = p[i];
                p[i] = p[ rev[i] ];
                p[ rev[i] ] = aux;
            }
        }
        
        for (int len = 2; len <= N; len <<= 1) {
            double angle = 2 * Math.PI / len;
            if (invert) {
                angle *= -1;
            }
            double[] wgo = { Math.cos(angle), Math.sin(angle) };
            
            for (int i = 0; i < N; i += len) {
                double [] w = {1, 0};
                for (int j = 0; j < (len >> 1); j++) {
                    double [] a = p[i + j];
                    double [] b = times( w, p[i + j + (len >> 1)] );
                    
                    p[i + j] = plus(a, b);
                    p[i + j + (len >> 1)] = minus(a, b);
                    w = times(w, wgo);
                }
            }
        
        }
        
        if (invert) for (int i = 0; i < N; i++) {
            p[i] = divide(p[i], N);
        }
    }
    
    
    

    // Complex data type and methods
    private double[] reciprocal(double [] a) {
        double scale = a[0]*a[0] + a[1]*a[1];
        return	new double [] { a[0] / scale, -a[1] / scale};
    }
    private double[] divide(double [] a, double [] b) {
        return times( a, reciprocal(b) );
    }
    private double[] divide(double [] a, int n) {
        double[] b = {n, 0};
        return  divide( a, b );
    }

    private double[] times(double [] a, double [] b) {
        double real = a[0] * b[0] - a[1] * b[1];
        double imag = a[0] * b[1] + a[1] * b[0];
        return new double [] { real, imag };
    }

    private double[] plus(double[] a, double[] b) {
        double real = a[0] + b[0];
        double imag = a[1] + b[1];
        return new double [] {real, imag};
    }
    private double[] minus(double[] a, double[] b) {
        double real = a[0] - b[0];
        double imag = a[1] - b[1];
        return new double [] {real, imag};
    }
    
}
