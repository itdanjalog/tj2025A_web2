package example.day11;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Example2 {
    public static void main(String[] args) {

        // -----------------------------------------------------------
        // [리스트 생성]
        // - List.of(...) 는 불변 리스트(추가/삭제 불가)를 만든다.
        // - int(기본타입)는 List에 담을 수 없으므로, 대신 Integer(래퍼클래스)를 사용한다.
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);

        // -----------------------------------------------------------
        // 1. stream() + forEach()
        // - 컬렉션(List, Set 등)에서 stream()을 생성하면 데이터 요소들을 차례로 처리할 수 있는 통로가 생긴다.
        // - forEach(소비자) 로 각 요소를 꺼내와 사용한다.
        numbers.stream()
                .forEach(x -> System.out.println("원본 데이터: " + x));

        // -----------------------------------------------------------
        // 2. stream() + map() + collect()
        // - map(변환 함수): 요소를 다른 값으로 변환
        // - collect(Collectors.toList()): 변환된 결과들을 다시 List로 모은다.
        List<Integer> result2 = numbers.stream()
                .map(x -> x * 2) // 모든 요소에 *2 연산
                .collect(Collectors.toList());
        System.out.println("모든 수에 *2 결과: " + result2);

        // -----------------------------------------------------------
        // 3. stream() + map() + forEach()
        // - map 으로 데이터를 가공하면서 바로 출력(forEach)도 가능하다.
        numbers.stream()
                .map(x -> x * 2)
                .forEach(x -> System.out.println("map *2 결과: " + x));

        // -----------------------------------------------------------
        // 4. stream() + filter()
        // - filter(조건식): 조건에 맞는 데이터만 남긴다.
        numbers.stream()
                .filter(x -> x % 2 == 0) // 짝수만 필터링
                .forEach(x -> System.out.println("짝수만 출력: " + x));

        // [비교] 기존 for문 방식 (같은 동작)
        for (int index = 0; index < numbers.size(); index++) {
            if (numbers.get(index) % 2 == 0) {
                System.out.println("for문 짝수: " + numbers.get(index));
            }
        }

        // -----------------------------------------------------------
        // 5. stream() + sorted()
        // - sorted(): 오름차순 정렬
        // - sorted(Comparator.reverseOrder()): 내림차순 정렬
        System.out.println("오름차순 정렬:");
        numbers.stream()
                .sorted()
                .forEach(x -> System.out.println(x));

        System.out.println("내림차순 정렬:");
        numbers.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(x -> System.out.println(x));

        // -----------------------------------------------------------
        // [심화 예제]
        // -----------------------------------------------------------

        // 6. distinct()
        // - 중복된 데이터를 제거하여 고유한 값만 남김
        // - SQL 의 DISTINCT 와 동일
        System.out.println("중복 제거(distinct):");
        numbers.stream()
                .distinct()
                .forEach(x -> System.out.println(x));

        // 7. limit(n)
        // - 처음부터 n개의 데이터만 가져오기
        // - 페이징 처리, 일부 샘플 데이터 추출에 사용
        System.out.println("앞에서 5개만(limit):");
        numbers.stream()
                .limit(5)
                .forEach(x -> System.out.println(x));

        // 8. skip(n)
        // - 앞의 n개 데이터를 건너뛰고 그 뒤부터 가져오기
        // - limit과 함께 사용하면 페이지네이션 구현 가능
        System.out.println("앞의 5개 건너뛰고 나머지(skip):");
        numbers.stream()
                .skip(5)
                .forEach(x -> System.out.println(x));

        // 9. reduce()
        // - 누적 연산을 통해 하나의 결과로 축소
        // - SQL 의 SUM, MAX, MIN 과 같은 집계 함수 개념
        // reduce(초기값, (누적값, 현재값) -> 연산)
        int sum = numbers.stream()
                .reduce(0, (acc, cur) -> acc + cur); // 합계
        System.out.println("reduce 합계: " + sum);

        int product = numbers.stream()
                .reduce(1, (acc, cur) -> acc * cur); // 곱
        System.out.println("reduce 곱: " + product);

        int max = numbers.stream()
                .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b); // 최대값
        System.out.println("reduce 최대값: " + max);

        int min = numbers.stream()
                .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b); // 최소값
        System.out.println("reduce 최소값: " + min);



    } // main end
} // class end
