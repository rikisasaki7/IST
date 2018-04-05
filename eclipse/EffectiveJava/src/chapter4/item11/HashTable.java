package chapter4.item11;

/**
 * @author Riki
 * 項目１１
 * cloneを注意してオーバーライドする
 * 可変オブジェクトを参照するフィールドがあっても、
 * 単純に再帰的にコピーすれば良いとは限らない例　ー＞LinkedListを使用したHashTable
 */
public class HashTable {
	private Entry[] buckets;
	private static class Entry{
		final Object key;
		Object value;
		Entry next;
		
		Entry(Object key, Object value, Entry next){
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
//		/*
//		 * このEntryから繋がっているLinkedListを再帰的にコピーする
//		 * しかし、この方法だとLinkedListの要素一つにつき、一つのスタックフレームを使用する
//		 * ※スタックフレーム：
//		 * https://docs.oracle.com/javase/jp/6/jdk/api/jpda/jdi/com/sun/jdi/StackFrame.html
//		 * リストが長いと、スタックオーバーフローが発生する
//		 */
//		Entry deepCopy(){
//			return new Entry(key, value,
//					next == null ? null : next.deepCopy());
//		}
		/*
		 * このEntryから繋がっているリンクリストをループしながらコピーする
		 * この方法だとスタックフレームを無駄に使用しない。
		 */
		Entry deepCopy(){
			Entry result = new Entry(key, value, next);
			for(Entry p=result;p.next!=null;p=p.next){
				p.next = new Entry(p.next.key, p.next.value, p.next.next);
			}
			return result;
		}
	}
	
//	/*
//	 * 不完全 - 内部状態が共有されている
//	 */
//	@Override
//	public HashTable clone(){
//		try{
//			HashTable result = (HashTable) super.clone();
//			result.buckets = buckets.clone(); // 配列の中身がまた可変オブジェクトの参照のため、結果的に内部が共有されている
//			return result;
//		} catch(CloneNotSupportedException e){
//			throw new AssertionError();
//		}
//	}
	
	/*
	 * 可変オブジェクトを参照している配列の中身一つ一つを
	 * for文でループしてnewしている。
	 */
	@Override
	public HashTable clone(){
		try{
			HashTable result = (HashTable) super.clone();
			result.buckets = new Entry[buckets.length];
			for(int i=0;i<buckets.length;i++){
				if(buckets[i] != null) result.buckets[i] = buckets[i].deepCopy();
			}
			return result;
		} catch(CloneNotSupportedException e){
			throw new AssertionError();
		}
	}
}
