// Copyright © 2008-2010 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://dimdwarf.sourceforge.net/LICENSE

package net.orfjackal.dimdwarf.scheduler;

import javax.annotation.CheckForNull;
import java.util.concurrent.TimeUnit;

public interface SchedulingStrategy {

    @CheckForNull
    SchedulingStrategy nextRepeatedRun();

    long getScheduledTime();

    long getDelay(TimeUnit unit);
}
