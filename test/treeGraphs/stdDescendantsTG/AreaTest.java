package treeGraphs.stdDescendantsTG;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import treeGraphs.painter.Point;

public class AreaTest {

	@AllArgsConstructor
	@NoArgsConstructor
	private class EventsState {
		private boolean heightChanged = false;
		private boolean widthChanged = false;
		private boolean xChanged = false;
		private boolean yChanged = false;
		
		public EventsState(int heightEvent, int widthEvent, int xEvent, int yEvent) {
			heightChanged = heightEvent != 0;
			widthChanged = widthEvent != 0;
			xChanged = xEvent != 0;
			yChanged = yEvent != 0;
		}

		public void setHeightChanged() {
			heightChanged = true;
		}
		public void setWidthChanged() {
			widthChanged = true;
		}
		public void setXChanged() {
			xChanged = true;
		}
		public void setYChanged() {
			yChanged = true;
		}

		public void clear() {
			heightChanged = false;
			widthChanged = false;
			xChanged = false;
			yChanged = false;
		}
		
		@Override
		public String toString() {
			return
				(heightChanged ? "1" : "0") +
				(widthChanged  ? "1" : "0") +
				(xChanged      ? "1" : "0") +
				(yChanged      ? "1" : "0");
		}
		
		public boolean equals(EventsState state) {
			return
				heightChanged == state.heightChanged &&
				widthChanged  == state.widthChanged &&
				xChanged      == state.xChanged &&
				yChanged      == state.yChanged;
		}
	}
	
	private Area area;
	private EventsState eventsState = new EventsState();
	
	@Before
	public void prepare() {
		area = new Area() {};

		area.setLocation(new Point(0, 0));
		area.setHeight(0);
		area.setWidth(0);
		
		area.setOnChangeHeight(() -> eventsState.setHeightChanged());
		area.setOnChangeWidth( () -> eventsState.setWidthChanged());
		area.setOnChangeX(     () -> eventsState.setXChanged());
		area.setOnChangeY(     () -> eventsState.setYChanged());
	}
	
	private boolean checkEvents(EventsState state) {
		return eventsState.equals(state);
	}

	void myAssert(int heightEvent, int widthEvent, int xEvent, int yEvent) {
		EventsState expectedState = new EventsState(heightEvent, widthEvent, xEvent, yEvent);
		
		String message =  System.lineSeparator() +
				"Actual: " + eventsState.toString() + System.lineSeparator() +
				"Expected: " + expectedState.toString();
		
		assertTrue(message, checkEvents(expectedState));
	}

	//Move
	@Test
	public void testMove_Y() {
		area.move(0, 10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testMove_NegativeY() {
		area.move(0, -10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testMove_X() {
		area.move(10, 0);
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testMove_NegativeX() {
		area.move(-10, 0);
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testMove_XY() {
		area.move(10, 10);
		myAssert(0, 0, 1, 1);
	}
	
	@Test
	public void testMove_NegativeXY() {
		area.move(-10, -10);
		myAssert(0, 0, 1, 1);
	}
	
	@Test
	public void testMove_00() {
		area.move(0, 0);
		myAssert(0, 0, 0, 0);
	}
	
	
	//MoveHorizontal
	@Test
	public void testMoveHorizontal_positive() {
		area.moveHorizontal(10);
		myAssert(0, 0, 1, 0);
	}

	@Test
	public void testMoveHorizontal_negative() {
		area.moveHorizontal(-10);
		myAssert(0, 0, 1, 0);
	}

	@Test
	public void testMoveHorizontal_zero() {
		area.moveHorizontal(0);
		myAssert(0, 0, 0, 0);
	}

	
	//MoveVertical
	@Test
	public void testMoveVertical_positive() {
		area.moveVertical(10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testMoveVertical_negative() {
		area.moveVertical(-10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testMoveVertical_zero() {
		area.moveVertical(0);
		myAssert(0, 0, 0, 0);
	}

	
	//SetLocation
	@Test
	public void testSetLocation_X() {
		area.setLocation(new Point(10, 0));
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testSetLocation_NegativeX() {
		area.setLocation(new Point(-10, 0));
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testSetLocation_Y() {
		area.setLocation(new Point(0, 10));
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testSetLocation_NegativeY() {
		area.setLocation(new Point(0, -10));
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testSetLocation_XY() {
		area.setLocation(new Point(10, 10));
		myAssert(0, 0, 1, 1);
	}
	
	@Test
	public void testSetLocation_NegativeXY() {
		area.setLocation(new Point(-10, -10));
		myAssert(0, 0, 1, 1);
	}
	
	@Test
	public void testSetLocation_00() {
		area.setLocation(new Point(0, 0));
		myAssert(0, 0, 0, 0);
	}

	
	//SetX
	@Test
	public void testSetX_positive() {
		area.setX(10);
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testSetX_negative() {
		area.setX(-10);
		myAssert(0, 0, 1, 0);
	}
	
	@Test
	public void testSetX_zero() {
		area.setX(0);
		myAssert(0, 0, 0, 0);
	}

	
	//SetY
	@Test
	public void testSetY_positive() {
		area.setY(10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testSetY_negative() {
		area.setY(-10);
		myAssert(0, 0, 0, 1);
	}
	
	@Test
	public void testSetY_zero() {
		area.setY(0);
		myAssert(0, 0, 0, 0);
	}

	
	//SetWidth
	@Test
	public void testSetWidth_positive() {
		area.setWidth(10);
		myAssert(0, 1, 0, 0);
	}
	
	@Test
	public void testSetWidth_negative() {
		area.setWidth(-10);
		myAssert(0, 0, 0, 0);
	}
	
	@Test
	public void testSetWidth_zero() {
		area.setWidth(0);
		myAssert(0, 0, 0, 0);
	}
	
	@Test
	public void testSetWidth_otherZero() {
		area.setWidth(100);
		eventsState.clear();
		
		area.setWidth(0);
		myAssert(0, 1, 0, 0);
	}

	
	//SetHeight
	@Test
	public void testSetHeight_positive() {
		area.setHeight(10);
		myAssert(1, 0, 0, 0);
	}
	
	@Test
	public void testSetHeight_negative() {
		area.setHeight(-10);
		myAssert(0, 0, 0, 0);
	}
	
	@Test
	public void testSetHeight_zero() {
		area.setHeight(0);
		myAssert(0, 0, 0, 0);
	}
	
	@Test
	public void testSetHeight_otherZero() {
		area.setHeight(100);
		eventsState.clear();
		
		area.setHeight(0);
		myAssert(1, 0, 0, 0);
	}
}
