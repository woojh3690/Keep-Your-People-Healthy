package woo.kyph;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqlHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String Data_Table = "dataTable";

    public SqlHelper(Context context) {
        super(context, "Database.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "create table " + Data_Table + " (num INTEGER, ingredient text, food text, recipe text, ingredient_efficacy text, food_efficacy text, good integer, bad integer, writer text);";
        try {
            db.execSQL(create);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        insertTimeTable(db); //시간표
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "drop table if exists " + Data_Table;
        String create = "create table " + Data_Table + " (num INTEGER, ingredient text, food text, recipe text, ingredient_efficacy text, food_efficacy text, good integer, bad integer, writer text);";
        try {
            db.execSQL(drop);
            db.execSQL(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertTimeTable(db);
    }

    public ArrayList<String[]> getData(String[] columns, String where, String limit) {
        return this.getData(false, columns, where, limit);
    }

    public ArrayList<String[]> getData(boolean distinct, String[] columns, String where, String limit) {
        ArrayList<String[]> items = new ArrayList<>();

        try {
            SQLiteDatabase db = super.getReadableDatabase();
            Cursor cursor = db.query(distinct, Data_Table, columns, where, null, null, null, null, limit);
            while (cursor.moveToNext()) {
                String[] data = new String[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    data[i] = cursor.getString(i);
                }
                items.add(data);
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return items;
    }

    //기본 보충시간표
    private void insertTimeTable(SQLiteDatabase db) {
        db.execSQL("insert into datatable values('1','감자','감자죽','[재료]\n찐 감자 160g, 불린 쌀 1/2컵, 표고버섯 10g, 양파 5g, 당근 5g, 물 5컵, 참기름\n1. 불린 쌀과 찐 감자는 1컵의 물을 넣고 믹서에 곱게 간다. \n2. 표고버섯, 당근, 양파는 잘게 다진다.\n3. 다진 야채를 참기름에 볶다가 싸과 감자를 넣고 퍼지도록 푹 끓인다.\n4. 먹기 좋게 농도를 맞춘다. (이때 간은 약간 싱겁게 하도록 하자.)','1. 비타민 C -> 철분 흡수 도움 , 혈관의 유지 작용\n2. 식이섬유 -> 콜레스테롤과 혈당의 저하\n3. 섬유성분 -> 콜레스테롤 발암물질 흡착, 배출\n4. 펙틴풍부 -> 혈액 순환의 원활\n5. 칼륨풍부 -> 전체적인 양자의 비율 유지','1. 저염식, 나트륨 과잉 섭취의 방지\n2. 칼륨의 풍부\n3.. 콜로스테롤의 방출에 큰 도움\n4. 혈압을 낮추는 효소 칼리크레인의 양 증가\n5. 양파 등의 재료 -> 혈액을 깨끗하게 만듬','0','0', 'Admin');");
        db.execSQL("insert into datatable values('2','감자','감자볶음','[재료]\n감자(얇게 채 썰기),채 썬 당근, 채썬 양파, 다진 마늘 1/2큰술, 올리브유, 참기름 1작은 술, 물 4-5큰술, 통깨\n1. 얇게 채 썬 감자를 물에 씻어내 전분기를 뺴 준다. \n2. 전분기를 뺀 감자에 약간의 소금을 뿌려서 재운다.\n3. 팬에 기름을 두르고 다진 마늘을 볶다가 나머지 재료를 넣고 볶는다.\n4. 어느 정도 익으면 약간의 물을 넣고 불의 세기를 강으로 한다.\n5. 잠깐 뜸을 들인다.','1. 비타민 C -> 철분 흡수 도움 , 혈관의 유지 작용\n2. 식이섬유 -> 콜레스테롤과 혈당의 저하\n3. 섬유성분 -> 콜레스테롤 발암물질 흡착, 배출\n4. 펙틴풍부 -> 혈액 순환의 원활\n6. 칼륨풍부 -> 전체적인 양자의 비율 유지','1. 칼퓸의 풍부\n2. 당근 등의 재료 -> 항산화 작용\n3. 비타민 풍부','0','0', 'Admin');");
        db.execSQL("insert into datatable values('3','아보카도','아보카도 바나나 스무디','[재료]\n아보카도 작은거 1, 바나나 1, 오트(귀리) 우유, 꿀 1작은 술\n1. 아보카도와 바나나의 껍질을 벗겨 믹서기에 넣고 간다.\n2. 귀리 우유를 붓고 잘 섞어 꿀을 넣는다.','1. 칼륨 -> 나트륨을 배출\n2. 붓기의 제거\n3. 카로티노이드 -> 항산화 기능 -> 혈관벽 강화\n4. 올레산 -> 콜레스테롤 감소','1. 바나나 -> 칼륨 풍부\n2. 아보카도 -> 혈관 강화, 나트륨 배출, 콜레스테롤 감소\n3. 귀리 -> 베타글루칸 -> 콜레스테롤 감소 + 칼슘','0','0', 'Admin');");
        db.execSQL("insert into datatable values('4','바나나','아보카도 바나나 스무디','[재료]\n아보카도 작은거 1, 바나나 1, 오트(귀리) 우유, 꿀 1작은 술\n1. 아보카도와 바나나의 껍질을 벗겨 믹서기에 넣고 간다.\n3. 귀리 우유를 붓고 잘 섞어 꿀을 넣는다.','1. 칼륨 다량 함유\n2. 콜레스테롤 수치 조절\n3. 심장박동 안정화\n4. 포만감 -> 체중조절 도움\n5. 철분 함유\n6. 비타민 B 공급','1. 바나나 -> 칼륨 풍부\n2. 아보카도 -> 혈관 강화, 나트륨 배출, 콜레스테롤 감소\n4. 귀리 -> 베타글루칸 -> 콜레스테롤 감소 + 칼슘','0','0', 'Admin');");
        db.execSQL("insert into datatable values('5','귀리','아보카도 바나나 스무디','[재료]\n아보카도 작은거 1, 바나나 1, 오트(귀리) 우유, 꿀 1작은 술\n1. 아보카도와 바나나의 껍질을 벗겨 믹서기에 넣고 간다.\n4. 귀리 우유를 붓고 잘 섞어 꿀을 넣는다.','1. 베타글루칸 -> 콜레스테롤 수치 감소\n2. 칼슘 현미의 4배\n3. 폴리페놀 -> 항산화 성분 -> 성인병 방지 ','1. 바나나 -> 칼륨 풍부\n2. 아보카도 -> 혈관 강화, 나트륨 배출, 콜레스테롤 감소\n5. 귀리 -> 베타글루칸 -> 콜레스테롤 감소 + 칼슘','0','0', 'Admin');");
        db.execSQL("insert into datatable values('6','시금치','시금치 무침','[재료]\n시금치, 국간장 1/2스푼, 소금, 다진마늘 1/3스푼, 참기름, 통깨\n1. 흐르는 물에 시금치를 씻고 뿌리 부분은 잘라준다.\n2.. 시금치를 뜨거운 물에 데친다. 이때 굵은 소금을 한꼬집 넣어준다. \n3. 시금치가 뜨거운 물에 잠길 걸 확인한 후 젓가락으로 저으면서 5초정도 후에 건져 식힌다. \n4. 식힌 시금치의 물기를 양손으로 짜준다.\n5. 국간장 1/2, 다진마늘 1/3, 참기름 조금, 통깨를 넣고 시금치와 잘 무쳐준다.','1. 베타카로틴 -> 비타민 A의 대체 공급원으로 유용 \n2. 비타민 K -> 혈관을 튼튼하게 해줌\n3. 동맥혈관 벽의 손상 방지\n4. 칼슘과 철분 풍부\n\n*주의 -> 신장 결석이 있는 사람은 각별한 주의가 필요함','1. 시금치를 살짝 데쳐 먹음으로서 영양소 파괴 최소화\n2. 기름과 무쳐서 흡수율 증가\n3. 비타민 A B1 C,섬유질, 요오드 등이 골고루 함유\n4. 혈관을 튼튼하게 해주고 빈혈도 예방\n5. 엽산 -> 심장병 등을 예방함','0','0', 'Admin');");
        db.execSQL("insert into datatable values('7','비트','비트 초절임','[재료]\n비트 반개, 파인애플, 식초 1스푼, 시럽 또는 올리고당 1스푼  (계량스푼 기준)\n1. 비트 반개를 먹기 좋게 자른다.\n2. 냄비에 20분 정도 찐다.\n3. 파인애플을 비트와 비슷한 크기로 자르고 찐 비트와 섞는다. (양은 비슷하게)\n4. 식초 1스푼, 시럽 또는 올리고당 1스푼을 넣어준다.','1. 질산염이 풍부하여 꾸준히 섭취할시 혈압 조절\n2. LDL 콜레스테롤과 중성지방을 감소시킴\n3. 베타레인 성분 -> 소염 작용 + 해독작용\n4. 풍부한 섬유질 -> 소화를 도움','1. 질산염 -> 꾸준히 섭취시 혈압 조절 가능\n2. 베타레인 성분 -> 소염 작용과 해독작용\n3. 섬유질 -> 소화를 도움\n4. 간단한 반찬으로 좋음','0','0', 'Admin');");
        db.execSQL("insert into datatable values('8','비트','비트 사과 샐러드','[재료]\n사과 1, 비트 1, 올리브유 1큰술, 식초 3큰술, 오렌지즙 3큰술, 천일염 약간, 후추가루 약간, 파슬리가루\n1. 비트는 껍질을 벗겨 채썬다.\n2. 사과는 껍질째 준비하여 채썬다.\n3. 나머지 분량의 재료를 섞어 드레싱을 만든다.\n4. 모든 재료를 버무리고 하룻밤 정도 재워준다.','1. 질산염이 풍부하여 꾸준히 섭취할시 혈압 조절\n2. LDL 콜레스테롤과 중성지방을 감소시킴\n3. 베타레인 성분 -> 소염 작용 + 해독작용\n4. 풍부한 섬유질 -> 소화를 도움','1. 질산염 -> 꾸준히 섭취시 혈압 조절 가능\n2. 베타레인 성분 -> 소염 작용과 해독작용\n3. 섬유질 -> 소화를 도움\n4. 간단한 반찬으로 좋음\n5.  사과와 비트는 콜레스테롤의 수치를 저하시키는 기능이 있음','0','0', 'Admin');");
        db.execSQL("insert into datatable values('9','연어','훈제연어 채소말이','[재료]\n훈제연어 180g, 파프리카 2개, 치커리 60g, 올리브유 4.5T, 레몬즙 1.5T, 꿀(올리고당)6t,씨겨자 3t, 소금과 후추\n1.훈제연어는 키친 타올로 눌로 기름기를 뺴 준다.\n2. 파프리카는 가늘게 채썰어 준다.\n3. 치커리는 씻고 물기를 털어 파프리카와 같은 길이로 썰어준다.\n4. 그 외의 재료를 다 섞어 준다. 이때 씨겨자는 기호에 따라 양을 조절하여 준다. (소스 제작)\n5. 훈제연어에 채썬 재료들을 넣고 돌돌 말아준다.\n6. 그 위에 만든 소스를 뿌려준다.','1. 오메가3 -> 콜레스테롤을 낮추고 성인병 예방\n+ 항응고 효과 -> 혈액순환 유지\n2. 불포화지방산 -> 혈액 속 혈전 생성 방지\n4. DHA -> 뇌기능 향상에 도움\n5. 고단백식품','1. 칼륨이 풍부함\n2. 혈액 순환을 돕고 혈액 속 혈전 생성을 방지함\n3. 콜레스테롤을 낮추어 성인병을 예방 \n4. 고단백식품\n5. 저염식으로 나트륨 섭취를 줄임','0','0', 'Admin');");
        db.execSQL("insert into datatable values('10','표고버섯','표고버섯탕수','[재료]\n생표고버섯 5개, 파프리카 1/4개, 홍고추 청고추 각 1, 찹쌀가루 2큰술, 감자전분 1큰술, 식초 2큰술, 설탕 1큰술, 다진마늘 반큰술, 참기름 약간, 후추, 호두 약간, 물 약간, 식용유\n1. 생표고버섯의 밑동을 제거하고 물에 잘 씻어낸다.\n2. 한입 크기로 자른 표고버섯에 참기름, 후추로 밑간을 한다.\n3. 찹쌀가루 2큰술, 물 약간을 섞어 묽게 반죽을 만들어 준다.\n4. 만들어진 반죽을 표고버섯의 겉에 약간 입혀 튀겨준다.\n5. 기름을 약간 두른 팬에 다진마늘, 고추를 넣고 볶다가 파프리카, 호두, 식초, 설탕을 넣고 마지막에 감자정분을 넣어 한소끔 끓인다\n6. 기름을 빠진 튀긴 표고버섯에 5에서 만든 소스를 부어준다.','1. 에리탄데닌 -> 혈중 콜레스테롤 축적 억제\n2. 췌장에서 인슐린 분비를 원활 -> 혈당 조절\n3. 피토스테린 -> 콜레스테롤 침착 방지\n4. 비타민 B12 -> 혈압을 낮춤\n5. 베타글루칸 -> 위장관을 깨끗이 + 염증 치료','1. 나트륨을 적게 만든 저염식\n2. 튀김가루가 아닌 찹쌀 가루 등을 사용하여 나트륨 배제\n3. 에리탄데닌과 비타민 B12 -> 혈압을 낮춤\n4. 피토시테린과 에리탄데닌 -> 콜레스테롤 낮추는데 기여','0','0', 'Admin');");
        db.execSQL("insert into datatable values('11','양배추','양배추볶음','[재료]\n양배추 1/4통 (500g), 식용유 약간, 소금 매우 약간\n1. 양배추는 가운데 심지를 잘래내고 얇게 채 썰어준다.\n2. 물에 2~3번 헹구고 체에 받혀준다.\n3. 달궈진 팬에 식용유를 약간 두르고 양배추와 소금을 넣고 양배추가 살짝 숨이 죽도록 빠르게 볶아낸다. (간은 약간 싱겁게)\n4. 잠깐 식혀 준 후 냉장보관 하였다가 차게 먹으면 된다.','1. 식이 섬유 -> 콜레스테롤 수치를 낮춤\n2. 혈증 호모시스테인을 떨어뜨려 동맥경화 방지\n3. 칼륨 -> 혈압의 저하를 도움\n4. 혈액을 맑게 하고 황산화 작용을 강화\n5. 메치오닌 성분이 위를 건강하게 해줌\n6. 나트륨 함량이 적음','1. 나트륨 함량이 적음\n2. 칼륨 -> 혈압의 저하를 도움\n3. 혈액을 맑게하고 황산화 작용을 강화\n4. 식이 성유 -> 코레스테롤 수치를 저하\n5. .혈증 호모시스테인을 떨어뜨려 동맥경화 방지','0','0', 'Admin');");
        db.execSQL("insert into datatable values('12','양파','양파밥','[재료]\n양파 1개, 쌀 1컵, 생 표고버섯 2장, 생수 1컵 반, 들기름 1T, 조선간장1T\n1. 쌀을 30분 정도 물에 불린다.\n2. 양파를 1/2로 자루고 뿌리 부분을 제거해 채를 썬다.\n3. 밥을 지을 냄비에 참기름 또는 들기름을 칠하고 채 썬 양파와 채 썬 표고버서을 넣고 국간장 약간, 들기름 약간을 넣고 볶아준다.\n4. 30분 정도 불려놓은 쌀을 넣고 볶는다. (맵쌀을 사용하는 것이 좋다.) ','1. 유화프로필 성분 - 콜레스테롤과 혈당치를 낮춤\n2. 케르세틴 성분 -> 혈관의 탄력성 강화 \n+ 혈압을 유지\n3. 지방과 혈전을 녹여 혈액을 깨끗하게 해줌\n4. 이소티오시아네이트 성분 -> 암 발생 억제','1. 유화프로필 성분 - 콜레스테롤과 혈당치를 낮춤\n2. 케르세틴 성분 -> 혈관의 탄력성 강화 \n+ 혈압을 유지\n3. 지방과 혈전을 녹여 혈액을 깨끗하게 해줌\n4. 이소티오시아네이트 성분 -> 암 발생 억제\n5. 양파를 밥과 함께 자연스럽게 섭취가 가능','0','0', 'Admin');");

    }
}
