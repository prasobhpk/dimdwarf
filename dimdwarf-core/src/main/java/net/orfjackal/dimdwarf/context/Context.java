// Copyright © 2008-2009, Esko Luontola. All Rights Reserved.
// This software is released under the MIT License.
// The license may be viewed at http://dimdwarf.sourceforge.net/LICENSE

package net.orfjackal.dimdwarf.context;

/**
 * @author Esko Luontola
 * @since 5.9.2008
 */
public interface Context {

    <T> T get(Class<T> service);
}
