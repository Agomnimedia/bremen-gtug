package org.gtugs.bremen.eventmanagement.android.out;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.data.Talk;

public interface Renderer {

	String renderEvent(Event event, DataAmount amount);
	String renderAttendee(Attendee attendee, DataAmount amount);
	String renderTalk(Talk talk, DataAmount amount);
}
