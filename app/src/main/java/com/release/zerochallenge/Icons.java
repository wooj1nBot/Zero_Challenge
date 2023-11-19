package com.release.zerochallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Icons {

    private static final int[] COFFEE = {R.drawable.coffee0, R.drawable.coffee1, R.drawable.coffee2, R.drawable.coffee3};
    private static final String[] COFFEE_NAME = {"아메리카노", "별다방", "던킨도나쓰", "얼죽아"};
    private static final String[] COFFEE_TEXT = {"", "", "", "얼어죽어도 아.아"};
    public static final Icon[] COFFEE_ICONS = {
            new Icon(COFFEE[0], COFFEE_NAME[0], COFFEE_TEXT[0], true, false, "coffee1"),
            new Icon(COFFEE[1], COFFEE_NAME[1], COFFEE_TEXT[1], true, false, "coffee2"),
            new Icon(COFFEE[2], COFFEE_NAME[2], COFFEE_TEXT[2], true, false, "coffee3"),
            new Icon(COFFEE[3], COFFEE_NAME[3], COFFEE_TEXT[3], true, true, "coffee4"),

    };
    private static final int[] MEALS = {R.drawable.meal1, R.drawable.meal2, R.drawable.meal3, R.drawable.meal4};
    private static final String[] MEALS_NAME = {"치즈버거", "초밥", "스트로베리 케익", "바깥쪽 세상에서 온 스테이크"};
    private static final String[] MEALS_TEXT = {"", "", "", "생일파티는 역시\n여기서!"};
    public static final Icon[] MEALS_ICONS = {
            new Icon(MEALS[0], MEALS_NAME[0], MEALS_TEXT[0], true, false, "meals1"),
            new Icon(MEALS[1], MEALS_NAME[1], MEALS_TEXT[1], true, false, "meals2"),
            new Icon(MEALS[2], MEALS_NAME[2], MEALS_TEXT[2], true, false, "meals3"),
            new Icon(MEALS[3], MEALS_NAME[3], MEALS_TEXT[3], true, true, "meals4"),
    };

    private static final int[] ALCOHOL = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};
    private static final String[] ALCOHOL_NAME = {"짹대니얼", "생맥 500cc", "칵테일 한잔", "블랙라벨"};
    private static final String[] ALCOHOL_TEXT = {"", "", "", "그대의 눈동자에\n건배"};
    public static final Icon[] ALCOHOL_ICONS = {
            new Icon(ALCOHOL[0], ALCOHOL_NAME[0], ALCOHOL_TEXT[0], true, false, "alcohol1"),
            new Icon(ALCOHOL[1], ALCOHOL_NAME[1], ALCOHOL_TEXT[1], true, false, "alcohol2"),
            new Icon(ALCOHOL[2], ALCOHOL_NAME[2], ALCOHOL_TEXT[2], true, false, "alcohol3"),
            new Icon(ALCOHOL[3], ALCOHOL_NAME[3], ALCOHOL_TEXT[3], true, true, "alcohol4"),
    };
    private static final int[] TRANSPORTATION = {R.drawable.trans1, R.drawable.trans2, R.drawable.trans3, R.drawable.trans4};
    private static final String[] TRANSPORTATION_NAME = {"택시", "버스", "스쿠터", "람보루기니"};
    private static final String[] TRANSPORTATION_TEXT = {"", "", "", "가속하는 내 멋짐..."};
    public static final Icon[] TRANSPORTATION_ICONS = {
            new Icon(TRANSPORTATION[0], TRANSPORTATION_NAME[0], TRANSPORTATION_TEXT[0], true, false, "transportation1"),
            new Icon(TRANSPORTATION[1], TRANSPORTATION_NAME[1], TRANSPORTATION_TEXT[1], true, false, "transportation2"),
            new Icon(TRANSPORTATION[2], TRANSPORTATION_NAME[2], TRANSPORTATION_TEXT[2], true, false, "transportation3"),
            new Icon(TRANSPORTATION[3], TRANSPORTATION_NAME[3], TRANSPORTATION_TEXT[3], true, true, "transportation4"),
    };

    private static final int[] RESALE = {R.drawable.resale1, R.drawable.resale2, R.drawable.resale3};
    private static final String[] RESALE_NAME = {"쇼핑백", "택배 박스", "고급 쇼핑백"};
    private static final String[] RESALE_TEXT = {"", "", "한번의 거래에도 정성과 사랑을 담아서"};
    public static final Icon[] RESALE_ICONS = {
            new Icon(RESALE[0], RESALE_NAME[0], RESALE_TEXT[0], false, false, "resale1"),
            new Icon(RESALE[1], RESALE_NAME[1], RESALE_TEXT[1], false, false, "resale2"),
            new Icon(RESALE[2], RESALE_NAME[2], RESALE_TEXT[2], false, true, "resale3"),
    };

    private static final int[] APP = {R.drawable.app1, R.drawable.app2, R.drawable.app3};
    private static final String[] APP_NAME = {"3성", "사과", "무트코인"};
    private static final String[] APP_TEXT = {"", "", "오늘도 무를 팔아서\n돈을 벌어볼까?"};
    public static final Icon[] APP_ICONS = {
            new Icon(APP[0], APP_NAME[0], APP_TEXT[0], false, false, "app1"),
            new Icon(APP[1], APP_NAME[1], APP_TEXT[1], false, false, "app2"),
            new Icon(APP[2], APP_NAME[2], APP_TEXT[2], false, true, "app3"),
    };

    private static final int[] PIGGY = {R.drawable.piggy1, R.drawable.piggy2, R.drawable.piggy3};
    private static final String[] PIGGY_NAME = {"돼지저금통", "체크카드", "재벌집 금고"};
    private static final String[] PIGGY_TEXT = {"", "", "저는 판교땅을\n사보겠습니다."};
    public static final Icon[] PIGGY_ICONS = {
            new Icon(PIGGY[0], PIGGY_NAME[0], PIGGY_TEXT[0], false, false, "piggy1"),
            new Icon(PIGGY[1], PIGGY_NAME[1], PIGGY_TEXT[1], false, false, "piggy2"),
            new Icon(PIGGY[2], PIGGY_NAME[2], PIGGY_TEXT[2], false, true, "piggy3"),
    };

    private static final int[] ETC = {R.drawable.etc1, R.drawable.etc2, R.drawable.etc3};
    private static final String[] ETC_NAME = {"통기타", "화이트\n일렉기타", "청춘과 열정"};
    private static final String[] ETC_TEXT = {"", "", "내 청춘은 이제서야\n시작이야"};
    public static final Icon[] ETC_ICONS = {
            new Icon(ETC[0], ETC_NAME[0], ETC_TEXT[0], false,false, "etc1"),
            new Icon(ETC[1], ETC_NAME[1], ETC_TEXT[1], false, false, "etc2"),
            new Icon(ETC[2], ETC_NAME[2], ETC_TEXT[2], false, false, "etc3"),
    };

    public static final Icon[] KEY_ICONS = {
            new Icon(R.drawable.key2, "3", "3000 \\", false,true, "key1"),
            new Icon(R.drawable.key2, "10", "9000 \\", false,true, "key2"),
            new Icon(R.drawable.key2, "30", "25000 \\", false,true, "key3"),
            new Icon(R.drawable.key2, "60", "40000 \\", false,true, "key4"),
    };
    public static final Icon[] ADS_ICONS = {
            new Icon(R.drawable.ad_off_48px, "광고 제거", "4000 \\", false,true, "removead1")
    };

    public static final List<String> Sku_ID_List_INAPP = Arrays.asList(
            COFFEE_ICONS[3].id, MEALS_ICONS[3].id, ALCOHOL_ICONS[3].id, TRANSPORTATION_ICONS[3].id, RESALE_ICONS[2].id, APP_ICONS[2].id, PIGGY_ICONS[2].id,
            KEY_ICONS[0].id, KEY_ICONS[1].id, KEY_ICONS[2].id, KEY_ICONS[3].id, ADS_ICONS[0].id);


    public static final Icon[][] REDUCE_ICON = {COFFEE_ICONS, MEALS_ICONS, ALCOHOL_ICONS, TRANSPORTATION_ICONS, ETC_ICONS};
    public static final Icon[][] SAVE_ICON = {RESALE_ICONS, APP_ICONS, PIGGY_ICONS, ETC_ICONS};
    public static final Icon[][] ICONS = {COFFEE_ICONS, MEALS_ICONS, ALCOHOL_ICONS, TRANSPORTATION_ICONS, ETC_ICONS, RESALE_ICONS, APP_ICONS, PIGGY_ICONS, ETC_ICONS};


    public static class Icon{

        int resId;
        String name;
        String text;
        boolean isReduce;
        boolean isPaid;
        String id;

        Icon(int resId, String name, String text, boolean isReduce, boolean isPaid, String id){
            this.resId = resId;
            this.name = name;
            this.text = text;
            this.isReduce = isReduce;
            this.isPaid = isPaid;
            this.id = id;
        }
    }




}
