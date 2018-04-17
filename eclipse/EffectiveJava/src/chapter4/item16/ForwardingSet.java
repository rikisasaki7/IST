package chapter4.item16;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * @author Riki
 * 項目１６
 * 継承よりコンポジションを選ぶ

 * 再利用可能な転送クラス
 */
public class ForwardingSet<E> implements Set<E>{

	/* コンポジション */
	private final Set<E> s;

	/* コンストラクタ */
	public ForwardingSet(Set<E> s){this.s = s;}

	/* 転送メソッド */
	public void clear(){s.clear();}
	public boolean contains(Object o){return s.contains(o);}
	public boolean isEmpty(){return s.isEmpty();}
	public int size(){return s.size();}
	public Iterator<E> iterator(){return s.iterator();}
	public boolean add(E e){return s.add(e);}
	public boolean remove(Object o){return s.remove(o);}
	public boolean containsAll(Collection<?> c){return s.containsAll(c);}
	public boolean addAll(Collection<? extends E> c){return s.addAll(c);}
	public boolean removeAll(Collection<?> c){return s.removeAll(c);}
	public boolean retainAll(Collection<?> c){return s.retainAll(c);}
	public Object[] toArray(){return s.toArray();}
	public <T> T[] toArray(T[] a){return s.toArray(a);}
	@Override public boolean equals(Object o){return s.equals(o);}
	@Override public int hashCode(){return s.hashCode();}
	@Override public String toString(){return s.toString();}

}