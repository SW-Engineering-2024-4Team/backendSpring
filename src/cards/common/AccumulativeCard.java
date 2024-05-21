package cards.common;

import java.util.Map;

public interface AccumulativeCard extends ActionRoundCard {
    Map<String, Integer> getAccumulatedResources(); // 누적된 자원을 반환하는 메서드
    void clearAccumulatedResources(); // 누적된 자원을 초기화하는 메서드

    // 자원 누적
    default void accumulateResources() {
        if (isAccumulative()) {
            Map<String, Integer> resources = getAccumulatedResources();
            for (Map.Entry<String, Integer> entry : resources.entrySet()) {
                resources.put(entry.getKey(), resources.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
    }
}
