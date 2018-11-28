/**
 * Segment tree (bottom-up version)
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;


public class BinarySegmentTree {
	
	int N;
	int [] tree;
	
	/**
	 * Crea un BinarySegmentTree para que soporte operaciones en el rango [0, size], inclusivo.
	 * @param size		La posicion maxima que manejara el arbol.
	 */
	public BinarySegmentTree(int size) {
		N = 1;
		while ( (N <<= 1) < size);
		tree = new int[N << 1];
	}
	
	/**
	 * Agrega, o elimina, n puntos en la posicion indicada. Si n < 0, entonces esta eliminando.
	 * Complejidad:		O(lg n)
	 * @param pos		Posicion del punto a agregar.
	 * @param n			Numero de puntos a agregar, o eliminar.
	 */
	void add(int pos, int n) {
		pos += N;
		while (pos > 0) {
			tree[pos] += n;
			pos >>= 1;
		}
   	}
	/**
	 * Asigna el valor n en la posicion indicada
	 * Complejidad:		O(lg n)
	 * @param pos		Posicion del punto a agregar.
	 * @param n			Valor a asignar
	 */
	void set(int pos, int n) {
		pos += N;
		tree[pos] = n;
		pos >>= 1;
		while (pos > 0) {
			tree[pos] = tree[pos << 1] + tree[(pos << 1) + 1];
			pos >>= 1;
		}
   	}
	
	/**
	 * Cuenta el numero de puntos que estan dentro del rango [l, r], inclusivo.
	 * Complejidad:		O(lg l + lg r)
	 * @param l			Inicio del rango.
	 * @param r			Fin del rango.
	 * @return			Numero de puntos que contiene el rango especificado.
	 */
	int getCount(int l, int r) {
		l += N;
		r += N;
		int ans = 0;
		while (l <= r) {
			if (l == r)	return	ans + tree[l];
			if ((l & 1) == 1)	ans += tree[l++];
			if ((r & 1) == 0)	ans += tree[r--];
			l >>= 1;
			r >>= 1;
		}
		return ans;
	}
}
