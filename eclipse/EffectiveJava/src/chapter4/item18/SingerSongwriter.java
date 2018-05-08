package chapter4.item18;

/*
 * 項目１８
 * 抽象クラスよりインタフェースを選ぶ
 * 歌手とソングライターをミックスインしたシンガーソングライターインタフェース
 * 抽象クラスで実現しようとすると、組み合わせだけクラスを作らなければならず、組み合わせ爆発問題が発生する
 * また、共通部分がなくなるので、似たようなメソッドが大量生産されてしまう。
 */
public interface SingerSongwriter extends Singer, Songwriter {
	String strum();
	void actSensitive();

}
