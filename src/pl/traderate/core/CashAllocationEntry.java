/*
 * Copyright (C) 2012 Karol Piczak <karol@dvl.pl>
 *
 * This file is part of the TradeRate package.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package pl.traderate.core;

import pl.traderate.core.exception.EntryInsertionException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

class CashAllocationEntry extends CashReallocationEntry {

	protected CashAllocationEntry(Account account, Portfolio portfolio, ArrayList<Tag> tags, Date date, String comment, BigDecimal amount) {
		super(account, portfolio, tags, date, comment, amount);
	}

	@Override
	public void attach() throws EntryInsertionException {
		account.addEntry(this);

		try {
			portfolio.addEntry(this);
		} catch (EntryInsertionException e) {
			account.removeEntry(this);
			throw e;
		}
	}

	@Override
	public void detach() throws EntryInsertionException {
		account.removeEntry(this);

		try {
			portfolio.removeEntry(this);
		} catch (EntryInsertionException e) {
			account.addEntry(this);
			throw e;
		}
	}

	@Override
	public void apply(Portfolio portfolio) {
		portfolio.applyEntry(this);
	}

	@Override
	public void apply(Account account) throws EntryInsertionException {
		account.applyEntry(this);
	}

}