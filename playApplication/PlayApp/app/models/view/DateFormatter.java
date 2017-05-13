package models.view;

import utils.ConfigUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日付用ビューモデル
 */
public class DateFormatter {

    private static final String defaultFormat = "yyyy年MM月dd日 HH時mm分ss秒";

    /**
     * 設定値app.models.view.defaultFormatに沿ったフォーマットを行い、返す
     *
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        String dateFormat = ConfigUtil.get("app.models.view.defaultFormat").getOrElse(defaultFormat);
        return new SimpleDateFormat(dateFormat).format(date);
    }

}