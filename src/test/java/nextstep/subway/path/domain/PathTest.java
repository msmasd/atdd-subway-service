package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Distance;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PathTest {

    private Line 신분당선;
    private Line 삼호선;
    private Line 이호선;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 서초역;
    private Station 교대역;

    @BeforeEach
    public void setUp() {
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        남부터미널역 = new Station("남부터미널역");
        서초역 = new Station("서초역");
        교대역 = new Station("교대역");
        신분당선 = new Line("신분당선", "red", 강남역, 양재역, new Distance(10));
        삼호선 = new Line("신분당선", "red", 양재역, 남부터미널역, new Distance(7));
        이호선 = new Line("신분당선", "red", 서초역, 교대역, new Distance(15));
    }

    @Test
    void 최단거리를_구할_수_있다() {
        Path path = new Path(Arrays.asList(신분당선, 삼호선));

        GraphPath<Station, DefaultWeightedEdge> result = path.findPath(강남역, 남부터미널역);

        assertThat(result.getVertexList()).containsExactly(강남역, 양재역, 남부터미널역);
        assertThat(result.getWeight()).isEqualTo(17);
    }

    @Test
    void 출발역과_도착역이_동일하면_최단거리를_구할_수_없다() {
        Path path = new Path(Arrays.asList(신분당선, 삼호선));

        assertThatIllegalArgumentException().isThrownBy(() -> path.findPath(강남역, 강남역));
    }

    @Test
    void 출발역과_도착역이_연결이_되어_있지_않으면_최단거리를_구할_수_없다() {
        Path path = new Path(Arrays.asList(신분당선, 삼호선));

        assertThatIllegalArgumentException().isThrownBy(() -> path.findPath(강남역, 서초역));
    }

}