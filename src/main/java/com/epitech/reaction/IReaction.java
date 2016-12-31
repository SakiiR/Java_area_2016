package com.epitech.reaction;


import com.epitech.utils.ErrorCode;

/**
 * This interface is implemented by all Reactions
 */
public interface        IReaction {
    public ErrorCode    run(Object data);
}
