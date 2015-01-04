/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

/**
 *
 * @author Joris (javadoc)
 */
public interface ICentraleBank{

    /**
     * Maakt een bedrag over van rekening1 bij bank1 naar rekening2 bij bank2,
     * mits het afschrijven van het bedrag van de rekening met nr bron niet
     * lager wordt dan de kredietlimiet van deze rekening
     *
     * @param bronReknr bronrekening
     * @param doelReknr doelrekening
     * @param bedrag het over te maken bedrag
     * @return true als gelukt, false als gefaald
     */
    public boolean maakOver(int bronReknr, int doelReknr, Money bedrag);
    
    /**
     * Berekend het eerst volgende vrije bankrekeningnummer
     * @return het eerst volgende vrije bankrekeningnummer
     */
    public int nextBankNr();
    
    /**
     * Voegt een bank toe aan de centrale bank
     * @param bank de toe te voegen bank
     * @return true als gelukt, false als gefaald
     */
    public boolean addBank(IBank bank);
    
    /**
     * Haalt de rekening op van het meegegeven rekeningnummer
     * @param rekenNr het rekeningnummer van de rekening
     * @return de bij het rekeningnummer hoorende rekening
     */
    public IRekening getRekening(int rekenNr);
    
}
