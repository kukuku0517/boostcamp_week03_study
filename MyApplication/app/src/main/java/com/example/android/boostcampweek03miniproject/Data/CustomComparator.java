package com.example.android.boostcampweek03miniproject.Data;
        import java.util.Comparator;

/**
 * Created by samsung on 2017-07-13.
 */

//CardItem을 비교하는 comparator
public class CustomComparator implements Comparator<CardItem> {

    private int sortType;

    //type에 따라 sorting 기준 변경(거리,인기,최신)
    public CustomComparator(int sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(CardItem o1, CardItem o2) { //내림차순 오름차순!!
        long obj1 = 0;
        long obj2 = 0;
        switch (sortType) {
            case 0: //거리
                obj1 = o1.getDistance();
                obj2 = o2.getDistance();
                break;
            case 1: //인기
                obj1 = o2.getLikes();
                obj2 = o1.getLikes();
                break;
            case 2: //시간
                obj1 = o1.getDate().getTime();
                obj2 = o2.getDate().getTime();
                break;
        }

        return (int) (obj1-obj2);
    }
}