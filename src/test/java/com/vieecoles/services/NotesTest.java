package com.vieecoles.services;

import org.junit.jupiter.api.Test;

import com.vieecoles.steph.entities.Notes;

import io.smallrye.common.constraint.Assert;

public class NotesTest {
	@Test
	public void initNotes() {
		Notes note = new Notes();
		Assert.assertTrue(note.getId() == 0L);
	}
}
