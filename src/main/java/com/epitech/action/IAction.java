package com.epitech.action;

import com.epitech.utils.ErrorCode;

/**
 * This interface is implemented by all Actions
 */
public interface        IAction {
    ErrorCode           run();
    String              getData();
}
