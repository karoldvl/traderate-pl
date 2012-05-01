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

package pl.traderate.desktop.view;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;
import org.netbeans.swing.outline.OutlineModel;
import pl.traderate.core.AccountDTO;
import pl.traderate.core.HoldingsDTO;
import pl.traderate.core.PortfolioDetailsDTO;
import pl.traderate.desktop.presenter.SummaryPresenter;

import java.math.BigDecimal;

public class SummaryViewModel extends GenericViewModel {

	protected SummaryView view;
	
	protected int nodeID;
	
	protected String nodeName;
	
	protected NodeType nodeType;

	public enum NodeType {
		ACCOUNT,
		PORTFOLIO
	}

	protected BigDecimal currentValue;

	protected BigDecimal openValue;

	protected BigDecimal paperGain;

	protected BigDecimal cashAvailable;

	protected BigDecimal aggregatedCash;

	protected BigDecimal realizedIncome;

	protected BigDecimal realizedCost;

	protected BigDecimal realizedGain;

	protected BigDecimal realizedTax;

	protected BigDecimal taxPaid;

	protected BigDecimal taxDue;
	
	protected HoldingsDTO holdings;

	private OutlineModel openHoldingsTreeTable;

	private OutlineModel closedHoldingsTreeTable;

	public SummaryViewModel(SummaryPresenter presenter) {
		super(presenter);

		view = new SummaryView(this, presenter);
		addObserver(view);

		// Make sure that both views reference the same object
		super.view = view;
	}

	public SummaryView getView() {
		return view;
	}

	public void setNode(PortfolioDetailsDTO portfolio) {
		this.nodeID = portfolio.ID;
		this.nodeName = portfolio.name;
		this.nodeType = NodeType.PORTFOLIO;
		this.cashAvailable = portfolio.cashBalance;
		this.aggregatedCash = portfolio.aggregatedCashBalance;
		this.holdings = portfolio.aggregatedHoldings;
		
		updateHoldings();
		notifyChange(SyncType.NODE);
	}

	public void setNode(AccountDTO account) {
		this.nodeID = account.ID;
		this.nodeName = account.name;
		this.nodeType = NodeType.ACCOUNT;
		this.cashAvailable = account.cashBalance;
		this.aggregatedCash = BigDecimal.ZERO;
		this.holdings = account.holdings;
		
		updateHoldings();
		notifyChange(SyncType.NODE);
	}

	private void updateHoldings() {
		openHoldingsTreeTable = new HoldingTable("Otwarte pozycje", holdings.equityHoldings, false).getOutlineModel();
		closedHoldingsTreeTable = new HoldingTable("Zamknięte pozycje", holdings.closedEquityHoldings, true).getOutlineModel();
	}

	public String getNodeName() {
		return nodeName;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public BigDecimal getOpenValue() {
		return openValue;
	}

	public BigDecimal getPaperGain() {
		return paperGain;
	}

	public BigDecimal getCashAvailable() {
		return cashAvailable;
	}

	public BigDecimal getAggregatedCash() {
		return aggregatedCash;
	}

	public BigDecimal getRealizedIncome() {
		return realizedIncome;
	}

	public BigDecimal getRealizedCost() {
		return realizedCost;
	}

	public BigDecimal getRealizedGain() {
		return realizedGain;
	}

	public BigDecimal getRealizedTax() {
		return realizedTax;
	}

	public BigDecimal getTaxPaid() {
		return taxPaid;
	}

	public BigDecimal getTaxDue() {
		return taxDue;
	}

	public OutlineModel getOpenHoldingsTreeTable() {
		return openHoldingsTreeTable;
	}

	public OutlineModel getClosedHoldingsTreeTable() {
		return closedHoldingsTreeTable;
	}

	public int getNodeID() {
		return nodeID;
	}

//:-- ViewModel sync types ---------------------------------------------------------

	public enum SyncType {
		NODE
	}
}