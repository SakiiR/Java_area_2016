package com.epitech.fixture;

import com.epitech.model.Area;
import com.epitech.repository.AreaRepository;

import java.util.List;

/**
 * This class is used to add default data
 * to the database when the server is starting.
 */
public class                    AreaFixture {
    private AreaRepository      areaRepository;

    /**
     * Constructor
     *
     * @param areaRepository the repository relatives to Areas
     */
    public                      AreaFixture(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    /**
     * Add an area to the database ( if not already exists )
     *
     * @param area the area to add
     */
    public void                 add(Area area) {
        List<Area>              areas = this.areaRepository.findAll();
        boolean                 found = false;

        for(Area a : areas) {
            if (a.getActionName().equals(area.getActionName()) && a.getReactionName().equals(area.getReactionName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            this.areaRepository.save(area);
        }
    }

    /**
     * Load all areas
     */
    public void                 init() {
        // add some areas
        this.add(new Area("GmailAttachmentsAction", "GoogleDriveSaveFileReaction", "gmail", "googledrive", "Save all google mail attachments in your google drive"));
    }

    /**
     * Delete all existing areas.
     */
    public void                 clear() {
        this.areaRepository.deleteAll();
    }
}