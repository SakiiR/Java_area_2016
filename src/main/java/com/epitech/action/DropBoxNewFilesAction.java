package com.epitech.action;

import com.epitech.utils.ErrorCode;

/**
 * Created by anakin on 02/01/17.
 */
public class                            DropBoxNewFilesAction implements IAction {
    private String                      token;

    private Object                      data = null;

    public                              DropBoxNewFilesAction(String token) {
        this.token = token;
    }

    public Object                       getData() {
        return this.data;
    }
    
    public ErrorCode                    run() {
        return ErrorCode.SUCCESS;
    }
}
