package com.release.zerochallenge;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Achieve {

    public static List<Item> reduceItems = Arrays.asList(
            new Item("무일푼 모험가", "항상 첫 발걸음은 가볍게", true, Type.TYPE_ALL, 1, "너의 시작을 응원해" +
                    "\n- 개발자", R.drawable.reduce1),
            new Item("작심삼일", "3일동안 무언가를 꾸준히 해본적이 있나요?", true, Type.TYPE_ALL, 3, "처음은 3일, 그다음은 3주, 그리고 3년" +
                    "*작약의 꽃말은, 새로운 시작", R.drawable.reduce2),
            new Item("커린이", "커피 한잔에 얼마였지?", true, Type.REDUCE_COFFEE, 5, "커피는 어둠처럼 검고, 재즈는 선율처럼 따뜻했다. 내가 그 조그마한 세계를 음미할때 풍경은 나를 축복했다." +
                    "\n- 무라카미 하루키", R.drawable.reduce3),
            new Item("파워 워커", "오늘부터 걷는걸 즐기기로 했어요.", true, Type.REDUCE_TRANSPORTATION, 5, "1시간을 걸으면 약 200칼로리가 소모됩니다.", R.drawable.reduce4),
            new Item("기타 수집가", "기타 줄은 보통 6개에요. ", true, Type.REDUCE_ETC, 6, "귀여운 기타 수집가가 되신것을 축하드려요." +
                    "\n- 개발자", R.drawable.reduce5),
            new Item("공공칠빵", "이 게임 알아요? (틀 아닙니다..)", true, Type.REDUCE_ALCOHOL, 7, "라떼는….술자리 최고 인기 게임이었단 말이여 @@" +
                    "\n- 개발자", R.drawable.reduce6),
            new Item("병아리 모험가", "무지출 유치원 개나리반 1번 모험가! ", true, Type.TYPE_ALL, 10, "스스로 알을 깨면 병아리가 되지만 남이 깨주면 계란후라이가 된다. " +
                    "\n- J 허슬러", R.drawable.reduce7),
            new Item("직장인 밴드 ", "일과 취미생활을 병행한다는것은..생각보다 어렵구나", true, Type.REDUCE_ETC, 20, "참음악이란, 세상만사 너머 마법과 같은것" +
                    "\n-  조엔 k 롤링 (해리포터 저자) ", R.drawable.reduce8),
            new Item("만보기", "30만보만 걸어볼까요. ", true, Type.REDUCE_TRANSPORTATION, 30, "나는 천천히 걷지만 절대 뒷걸음질 치지 않는다." +
                    "\n- 에이브러햄 링컨", R.drawable.reduce9),
            new Item("혼술의 달인", "퇴근후 적막하지만 편안한 집에서 술을 기울이면" +
                    "마음도, 지갑도 편해지곤 합니다. ", true, Type.REDUCE_ALCOHOL, 50, "인생은 짧다. 그러나 술잔을 비울 시간은 아직 충분하다. " +
                    "\n- 노르웨이 속담", R.drawable.reduce10),
            new Item("커른이", "쓴 커피를 마시는 나.. 어른이자너 ", true, Type.REDUCE_COFFEE, 50, "내게 정신을 차리게 만드는 것은 진한 커피, 아주 진한 커피이다. 커피는 내게 온기를 주고, 특이한 힘과 기쁨과 쾌락이 동반된 고통을 불러 일으킨다." +
                    "\n- 나폴레옹", R.drawable.reduce11),
            new Item("그라데이션", "너의 색깔로 조금씩 물들어 가고 있는 달력", true, Type.TYPE_ALL, 50, "달콤한 색감이 물들어 조금씩 정신을 차렸을땐 알아볼 수도 없지 가득 찬 마음이 여물다 못해 터지고 있어 " +
                    "\n- 10CM '그라데이션' 가사 ", R.drawable.reduce12),
            new Item("파발마", "부산부터 한양까지 ", true, Type.REDUCE_TRANSPORTATION, 50, "누우면 죽고 걸으면 산다." +
                    "\n- 김영길 한의사", R.drawable.reduce13),
            new Item("낭만 수집가", "다만 내 삶에 충실하기로 했어요. 그게 바로 낭만인걸요.", true, Type.REDUCE_ETC, 60, "다른 사람이 칭찬을 하던 비난을 하던 나는 개의치 않는다. 다만 내 감정에 충실히 따를 뿐." +
                    "\n-  모차르트", R.drawable.reduce14),
            new Item("홈카페 장인", "이제 대세는, 집에서 내려 마시는 커피야", true, Type.REDUCE_COFFEE, 100, "내가 좋아하는 것은 향기다. 집 근처에서 커피콩을 볶을 때면 나는 서둘러 창문을 열어 그 향기를 모두 받아들인다. " +
                    "\n- 장 자크 루소 ", R.drawable.reduce15),
            new Item("인간 KTX", "서울부터 부산까지 KTX로 3시간..?" +
                    "까짓꺼 걸어갈게~ ", true, Type.REDUCE_TRANSPORTATION, 100, "진정 위대한 모든 생각은 걷기로부터 나온다." +
                    "\n- 프리드리히 니체", R.drawable.reduce16),

            new Item("어엿한 모험가", "무지출 챌린지를 시작한지 얼마나 되었을까요?" +
                    "가끔씩은 뒤돌아서 나의 발자취를 확인해보세요. ", true, Type.TYPE_ALL, 100, "어느새 점이었던 나의 발자국은 하나의 길이 되어 내 뒤를 밝혀주는 등불이 되어 있었다. " +
                    "\n- 개발자", R.drawable.reduce17),
            new Item("오늘저녁은 치맥이닭", "즐거운 게임을 하며, 맥주 한 캔 어떠세요 ", true, Type.REDUCE_ALCOHOL, 111, "맥주 첫모금의 맛을 당할만한 것은 세상에 없다. " +
                    "\n- 루터", R.drawable.reduce18),
            new Item("무지출 스타일", "나만의 스타일을 갖고, 자랑스럽게 이야기 하며 뿌듯해 할 수 있는 그런 사람이 되어볼까요.", true, Type.REDUCE_ETC, 135, "세계에서 가장 유명하고 인기 있는 언어는 음악이다. " +
                    "\n-  싸이", R.drawable.reduce19),
            new Item("커피 마스터", "MASTER OF COFFEE", true, Type.REDUCE_COFFEE, 200, "이제 누가 뭐라해도, 너가 내리는 커피가 세상에서 제일 맛있어." +
                    "\n- 개발자", R.drawable.reduce20),
            new Item("노련한 모험가", "이제는 누군가의 등불이자 멘토가 될 정도로 성장한 당신", true, Type.TYPE_ALL, 200, "혼자 걸으면 더 빨리 갈 수 있다. 하지만 둘이 걸으면 더 멀리 갈 수 있다. (어플 홍보 부탁드려요 ♥) " +
                    "\n- 아프리카 속담", R.drawable.reduce21),
            new Item("청바지", "청춘은 바로 지금부터! ", true, Type.REDUCE_ALCOHOL, 250, "여기계신분들의 기쁨이 저의 기쁨입니다. " +
                    "\n- 여기저기 (건배사)", R.drawable.reduce22),
            new Item("야생의 뚜벅초", "어엇..머리에서 뭐가 자라나는데..", true, Type.REDUCE_TRANSPORTATION, 250, "달빛속에서도 광합성을 하여 걸어다닐 수 있다. " +
                    "\n- 나무위키 '뚜벅초' 발췌", R.drawable.reduce23),
            new Item("작은 거인", "시작은 미약하였으나, 그 끝은 창대하리", true, Type.TYPE_ALL, 365, "내가 다른 사람보다 더 멀리 내다볼 수 있다면 그것은 거인의 어깨 위에 서 있었기 때문이다." +
                    "\n- 아이작 뉴턴", R.drawable.reduce24),
            new Item("술자리 이태백", "그대 눈동자에 건배", true, Type.REDUCE_ALCOHOL, 365, "꽃 사이에 술 한병 놓고 벗도 없이 홀로 마신다. 잔을 들어 달을 청하니, 그림자까지 세 사람이 되었네." +
                    "\n-  이태백 시 [월화독백 中] ", R.drawable.reduce25),
            new Item("Cupper", "일반인을 넘어 커피 전문가의 길을 걷게 되었어요.", true, Type.REDUCE_COFFEE, 365, "커퍼(cupper)란? 커피의 맛과 향, 특성이나 개성을 파악해 내는 사람" +
                    "생두의 등급을 매기며 원산지 감별이 가능함" +
                    " \n- 위키백과", R.drawable.reduce26),
            new Item("마라토너", "42.195km 를 넘어..", true, Type.REDUCE_TRANSPORTATION, 421, "인생은 반환점 없는 마라톤이라고 할 수 있지요. 되돌이킬 수 없는 인생을 후회없이 마무리하기 위해 언제나 최선을 다해야하는게 중요하지요" +
                    "\n- 故 손기정", R.drawable.reduce27),
            new Item("스피리터스", " 세계에서 가장 도수가 높은 술 ", true, Type.REDUCE_ALCOHOL, 960, " 폴란드에서 생산되고 있는 96도의 술 " +
                    " 미국의 일부 주에서는 폭발물로 취급돼, 판매가 중단되었다고 합니다. " +
                    " 폭탄같은 그대의 인내와 끈기에 건배 ! " +
                    " \n- 개발자 ", R.drawable.reduce28),
            new Item("우주 커피", "지구를 넘어 우주에 널리 알려진 커피 장인의 길 ", true, Type.REDUCE_COFFEE, 1000, "한 분야에 이정도로 꾸준함을 유지하는 당신은 이미 커피뿐만이 아니라, 그 어느곳에서도 성공할 수 있는 사람입니다. 여기까지 이용해주셔서 감사합니다. 앞으로도 잘 부탁드립니다. " +
                    "\n-  개발자 ", R.drawable.reduce29),
            new Item("우주 워커", "지구의 둘레는 약 4만120km 입니다. ", true, Type.REDUCE_TRANSPORTATION, 1000, "마라톤을 약 950번 하면 지구를 한바퀴 돌 수 있는 거리가 됩니다. " +
                    "생각보다 작은 횟수에 놀라셨나요? " +
                    "닿을 수 없다고, 불가능하다고 생각한 일도 어쩌면 닿을 수 있는 거리에 있을수도 있습니다. 지금 당신이 무언갈 이룬 순간처럼요. " +
                    "\n- 개발자", R.drawable.reduce30),
            new Item("우주 애주가", "푸른 지구를 바라보며, 은하를 담은 칵테일 한잔..", true, Type.REDUCE_ALCOHOL, 1000, "광활하지만 적막하며, 고요한 우주. 그 안을 유영하며 조용히 술잔을 기울이는 것만큼 낭만적인 일이 또 있을까요. 우주는 먼 존재이지만 여기까지 걸어온 당신에게는 그 신비로운 곳에서 술잔을 기울일 자격이 있습니다." +
                    "\n-  개발자", R.drawable.reduce31)
    );



    public static List<Item> saveItems = Arrays.asList(
            new Item("미어캣 거래자", " 쭈뼛 쭈뼛.. 내 거래자님은 누구지… 혹시 당신..?", false, Type.SAVE_RESALE, 1, "부끄러웠지만 뭔가 뿌듯해..좋은 주인 만나길!" +
                    "\n- 개발자", R.drawable.save1),
            new Item("앱테크 LV1 ", "어플 설치하고, 포인트 출금하고…아휴 복잡해", false, Type.SAVE_APP, 1, "애플리케이션 + 재테크의 합성어, 스마트폰과 같은" +
                    "모바일 디바이스의 앱 광고를 보면 주는 적립금이나 " +
                    "커피숍 등의 매장 이용 쿠폰을 받으며 일정 금액이 " +
                    "쌓이면 현금화를 하는 방식 " +
                    "\n-  트렌드 지식사전", R.drawable.save2),
            new Item("아기 돼지", "문방구에서 사온 아기 돼지에요! 이름을 뭐로 지어줄까..", false, Type.SAVE_PIGGY, 1, "윌버 어때? " +
                    "*샬롯의 거미줄에 등장하는 아기 돼지", R.drawable.save3),
            new Item("일렉기타", "가끔은 아무도 없는 공간에서 소리지르고 싶을 때가 있어요.", false, Type.SAVE_ETC, 5, "제 마음을 대변해주는 일렉기타", R.drawable.save4),
            new Item("눈치 빠른 너구리", " 이사람…내 물건을 살 사람이구나 ! ", false, Type.SAVE_RESALE, 10, "너구리의 특징은 일단 오동통한게 특징이며, 미역이 항상 함께있습니다." +
                    "\n- 개발자", R.drawable.save5),
            new Item("무지출 챌린지 막내 상인", " 중고 거래 그거… 우리 무지출챌린지에 도움이 되는기가?", false, Type.SAVE_RESALE, 30, "하루 24시간, 1년 365일, 시간만큼은 누구에게나 공평하다고 말한다. 시간은 결코 공평하지 않다. 이 세상 모든 것들이 다 그런 것처럼." +
                    "\n- 재벌집 막내아들 , 진도준 대사 ", R.drawable.save6),
            new Item("앱테크 LV2", "캐시워크뿐만 아니라, 앱테크의 범위가 " +
                    "너무나 많아졌고 다양해졌어", false, Type.SAVE_APP, 30, "앱테크 추천 어플1" +
                    "엠브레인: 인터넷/모바일 설문조사 및 좌담회를 통해 간편하게 현금화가 가능한 어플", R.drawable.save7),
            new Item("귀여운 토끼 거래자", "과연 귀엽기만 할까요..?", false, Type.SAVE_RESALE, 33, "지혜로운 토끼는 숨을 3개의 굴을 파놓는다." +
                    " \n- 교토삼굴", R.drawable.save8),
            new Item("베니스의 샤일록", "샤일록은 사실 그리 나쁜 사람이 아녔습니다. ", false, Type.SAVE_RESALE, 45, "베니스는 유대인을 무시하고 경멸하며 차별하였고, 샤일록은 한명의 유대인으로써 베니스가 자랑하는 율법을 통해 안토니오를 죽이고자 했습니다. " +
                    "제가 그에게 요구하는 살덩이 1파운드는 비싸게 산 것이라고요. 그건 내 거예요. 난 그걸 갖겠다는 겁니다. 그걸 가로막는다면, 당신네들 법이란 거 개판이오. 베니스 칙령도 아무 쓸모 없소." +
                    "\n-  베니스의 상인 4막 1장", R.drawable.save9),
            new Item("친구가 생긴 아기 돼지", "조금씩 성장하던 윌버는 어느날 작고 귀여운 친구가 생겼어요.", false, Type.SAVE_PIGGY, 50, "나한테 넌 근사한 돼지야. 그거면 된거야." +
                    "\n- 영화 샬롯의 거미줄 / 샬롯의 대사", R.drawable.save10),
            new Item("모으기 달인", "티끌 모아 태산!", false, Type.SAVE_ALL, 50, "처음부터 태산을 옮기기는 어렵지만, 시간은 결국 " +
                    "우리편이랍니다. " +
                    "\n- 개발자", R.drawable.save11),
            new Item("숙련된 여우 거래자", "어린왕자의 여우를 기억하시나요?", false, Type.SAVE_RESALE, 77, "너는 나에게 이 세상에 단 하나뿐인 존재가 되는거고, 나도 너에게 이 세상에 단 하나뿐인 존재가 되는 거지. " +
                    "\n-  생 텍 쥐페리 (어린왕자 中)", R.drawable.save12),
            new Item("미루지 말기 ", "내일의 나에게 맡기지 말자.", false, Type.SAVE_ALL, 80, "yesterday you said tomorrow. Just Do it" +
                    "\n- 나이키", R.drawable.save13),
            new Item("황금 당근", "딱 100번만 홧팅 해봅시다. 넘어지는걸 두려워하지 말구요.", false, Type.SAVE_RESALE, 100, "어렸을 때는 넘어지는게 겁이 안나서 그래요. 어른이 될수록 넘어지는것에 두려움이 있잖아요" +
                    "\n-  놀면뭐하니 84화 당근마켓편 유재석", R.drawable.save14),
            new Item("앱테크 LV3", "시작은 미약할 수 있으나, 그 끝은 창대하리.", false, Type.SAVE_APP, 100, "절약을 과시하는 풍조가 만연해지고 있다." +
                    "그런 시기에 조금이라도 저축을 하려고 노력하는 당신의 길을 응원합니다." +
                    "\n-  개발자", R.drawable.save15),

            new Item("에릭 클랩튼", "에릭 클랩튼 \n-  CHANGE THE WORLD", false, Type.SAVE_ETC, 100, "내가 세상을 바꿀 수 있다면 당신 우주의 빛이 되겠어요" +
                    "\n-  에릭 클랩튼", R.drawable.save16),
            new Item("윌버", "작게 태어난게 죄인가요? 나도 작게 태어났으면 죽일건가요?", false, Type.SAVE_PIGGY, 170, "샬롯은 나의 친구잖아요. 영원한 나의 친구가 되기로 약속했잖아요." +
                    "\n- 영화 샬롯의 거미줄 / 샬롯의 대사", R.drawable.save17),
            new Item("두 번 고민하는 거래자", "한번 더 생각해보겠어요? " +
                    "한번 더 생각해보겠어요?", false, Type.SAVE_RESALE, 222, "고민은 가격 상승만 가져올 뿐 " +
                    "\n-  개발자", R.drawable.save18),
            new Item("앱테크 LV4", "현명한 소비를 도와주는 어플도 앱테크라 할 수 있지.", false, Type.SAVE_APP, 234, "앱테크 추천 어플2" +
                    "니콘내콘: 기프티콘을 판매(현금화 가능) 할수도 있고, 기프티콘을 싼 값에 구매하여 소비 절약에 일조할 수 있는 어플", R.drawable.save19),
            new Item("기적", "자꾸 샬롯 이야기만 해서 죄송함다.. (그치만 너무 감명깊게 보았는걸요)", false, Type.SAVE_PIGGY, 365, "My miracle is you" +
                    "\n- 영화 샬롯의 거미줄 / 샬롯의 대사", R.drawable.save20),
            new Item("원기옥", "추억의 \"그\" 만화", false, Type.SAVE_ALL, 500, "대지여, 바다여, 그리고 살아있는 모든 것들이여.." +
                    "나에게 아주 조금씩만 원기를 나누어줘." +
                    "\n- 손오공", R.drawable.save21),
            new Item("우주 상인", "우주에서 가장 빛나는 물건을 판매 합니다.", false, Type.SAVE_RESALE, 1000, "그 물건은 비록 비싼 가격은 아닐지라도" +
                    "저의 추억과 정성 모두를 담은, 그리고 새로운 주인을 만나서 빛날 준비가 되어있는 우주에서 제일 빛나는 물건입니다. 저랑..당근하실래요?" +
                    "\n-  개발자", R.drawable.save22),
            new Item("앱테크 LV MAX", "드디어 만렙", false, Type.SAVE_APP, 1000, "연간 앱테크 수익 정산을 해보면서 뒤를 돌아봅시다. 내가 걸어온 길이 반짝반짝 빛나고 있는게 보이시나요?" +
                    "\n- 개발자", R.drawable.save23),
            new Item("우주 돼지", "작은 친구들은 작지만 소중하단 것을 깨달았어요. ", false, Type.SAVE_PIGGY, 1000, "처음 마음을 다잡고 시작한 잔돈저금통 챌린지를 한지 벌써 천일이 넘었네요. 그만큼 당신이 모아온 작은 꿈의 조각은 이제는 결코 작지 않다는 것을 깨닫게 되었어요. \n- 개발자", R.drawable.save24)
    );


    public static Item getItem(String name){
        for(Item item : reduceItems){
            if(item.name.equals(name)) return item;
        }
        for(Item item : saveItems){
            if(item.name.equals(name)) return item;
        }
        return null;
    }


    public static ArrayList<Item> check(ZeroUser user){
        ArrayList<Item> list = new ArrayList<>();

        for(String key : user.achieve_reduce.keySet()){
            int t = Integer.parseInt(key);
            int count = user.achieve_reduce.get(key);
            for(Item item : reduceItems){
                if(item.type == t){
                    if(count >= item.number && !user.achieve.containsKey(item.name)){
                        list.add(item);
                    }
                }
            }
        }

        for(String key : user.achieve_save.keySet()){
            int t = Integer.parseInt(key);
            int count = user.achieve_save.get(key);
            for(Item item : saveItems){
                if(item.type == t){
                    if(count >= item.number && !user.achieve.containsKey(item.name)){
                        list.add(item);
                    }
                }
            }
        }

        for(Item item : reduceItems){
            if(item.type == Type.TYPE_ALL){
                if(user.achieve_count >= item.number && !user.achieve.containsKey(item.name)){
                    list.add(item);
                }
            }
        }

        return list;
    }


    public static class Item implements Serializable {
        String name;
        String hint;
        boolean isReduce;
        int type;
        int number;
        String text;
        int resID;

        public Item(String name, String hint, boolean isReduce, int type, int number, String text, int resID){
            this.name = name;
            this.hint = hint;
            this.isReduce = isReduce;
            this.type = type;
            this.number = number;
            this.text = text;
            this.resID = resID;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", hint='" + hint + '\'' +
                    ", isReduce=" + isReduce +
                    ", type=" + type +
                    ", number=" + number +
                    ", text='" + text + '\'' +
                    ", resID=" + resID +
                    '}';
        }
    }
}
