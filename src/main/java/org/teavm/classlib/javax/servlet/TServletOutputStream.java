package org.teavm.classlib.javax.servlet;

import org.teavm.classlib.java.io.TOutputStream;

/**
 * Provides an output stream for sending binary data to the
 * client. A <code>ServletOutputStream</code> object is normally retrieved 
 * via the {@link ServletResponse#getOutputStream} method.
 *
 * <p>This is an abstract class that the servlet container implements.
 * Subclasses of this class
 * must implement the <code>java.io.OutputStream.write(int)</code>
 * method.
 *
 * 
 * @author         Various
 *
 * @see         ServletResponse
 *
 */

public abstract class TServletOutputStream extends TOutputStream {}