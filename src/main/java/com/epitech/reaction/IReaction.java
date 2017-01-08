package com.epitech.reaction;


import com.epitech.utils.ErrorCode;

/**
 * This interface is implemented by all Reactions
 */
public interface        IReaction {
    /**
     * This is the main method.
     *
     * @param data the data returned by the action.
     * @return an error code.
     */
    public ErrorCode    run(Object data);
}
