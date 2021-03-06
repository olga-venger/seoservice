/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure.internal;

/**
 * Это для моделирования разных числовых диапазонов + единицы изменерия
 */
public class NumbersWithUnitToken extends com.pullenti.ner.MetaToken {

    public NumbersWithUnitToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        if(_globalInstance == null) return;
    }

    public Double singleVal;

    public Double plusMinus;

    public boolean plusMinusPercent;

    public boolean fromInclude;

    public Double fromVal;

    public boolean toInclude;

    public Double toVal;

    public boolean about;

    public boolean not = false;

    public java.util.ArrayList<UnitToken> units = new java.util.ArrayList<UnitToken>();

    public NumbersWithUnitToken divNum;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (singleVal != null) {
            if (plusMinus != null) 
                res.append("[").append(singleVal).append(" ±").append(plusMinus).append((plusMinusPercent ? "%" : "")).append("]");
            else 
                res.append(singleVal);
        }
        else {
            if (fromVal != null) 
                res.append((fromInclude ? '[' : ']')).append(fromVal);
            else 
                res.append("]");
            res.append(" .. ");
            if (toVal != null) 
                res.append(toVal).append((toInclude ? ']' : '['));
            else 
                res.append("[");
        }
        for (UnitToken u : units) {
            res.append(" ").append(u.toString());
        }
        if (divNum != null) {
            res.append(" / ");
            res.append(divNum);
        }
        return res.toString();
    }

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> createRefenetsTokensWithRegister(com.pullenti.ner.core.AnalyzerData ad, String name, boolean regist) {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        for (UnitToken u : units) {
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(u.createReferentWithRegister(ad), u.getBeginToken(), u.getEndToken(), null);
            res.add(rt);
        }
        com.pullenti.ner.measure.MeasureReferent mr = new com.pullenti.ner.measure.MeasureReferent();
        String templ = "1";
        if (singleVal != null) {
            mr.addValue(singleVal);
            if (plusMinus != null) {
                templ = ("[1 ±2" + (plusMinusPercent ? "%" : "") + "]");
                mr.addValue(plusMinus);
            }
            else if (about) 
                templ = "~1";
        }
        else {
            if (not && ((fromVal == null || toVal == null))) {
                boolean b = fromInclude;
                fromInclude = toInclude;
                toInclude = b;
                Double v = fromVal;
                fromVal = toVal;
                toVal = v;
            }
            int num = 1;
            if (fromVal != null) {
                mr.addValue(fromVal);
                templ = (fromInclude ? "[1" : "]1");
                num++;
            }
            else 
                templ = "]";
            if (toVal != null) {
                mr.addValue(toVal);
                templ = (templ + " .. " + num + (toInclude ? ']' : '['));
            }
            else 
                templ += " .. [";
        }
        mr.setTemplate(templ);
        for (com.pullenti.ner.ReferentToken rt : res) {
            mr.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_UNIT, rt.referent, false, 0);
        }
        if (name != null) 
            mr.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_NAME, name, false, 0);
        if (divNum != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> dn = divNum.createRefenetsTokensWithRegister(ad, null, true);
            com.pullenti.unisharp.Utils.addToArrayList(res, dn);
            mr.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_REF, dn.get(dn.size() - 1).referent, false, 0);
        }
        com.pullenti.ner.measure.MeasureKind ki = UnitToken.calcKind(units);
        if (ki != com.pullenti.ner.measure.MeasureKind.UNDEFINED) 
            mr.setKind(ki);
        if (regist && ad != null) 
            mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(mr), com.pullenti.ner.measure.MeasureReferent.class);
        res.add(new com.pullenti.ner.ReferentToken(mr, getBeginToken(), getEndToken(), null));
        return res;
    }

    public static java.util.ArrayList<NumbersWithUnitToken> tryParseMulti(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean canOmitNumber, boolean _not, boolean canBeNon) {
        if (t == null || (t instanceof com.pullenti.ner.ReferentToken)) 
            return null;
        if (t.isChar('(')) {
            java.util.ArrayList<NumbersWithUnitToken> res0 = tryParseMulti(t.getNext(), addUnits, false, canOmitNumber, canBeNon);
            if (res0 != null && res0.get(res0.size() - 1).getEndToken().getNext() != null && res0.get(res0.size() - 1).getEndToken().getNext().isChar(')')) {
                res0.get(res0.size() - 1).setEndToken(res0.get(res0.size() - 1).getEndToken().getNext());
                return res0;
            }
        }
        NumbersWithUnitToken mt = tryParse(t, addUnits, canOmitNumber, _not, canBeNon);
        if (mt == null) 
            return null;
        java.util.ArrayList<NumbersWithUnitToken> res = new java.util.ArrayList<NumbersWithUnitToken>();
        if ((mt.getWhitespacesAfterCount() < 2) && MeasureHelper.isMultChar(mt.getEndToken().getNext())) {
            NumbersWithUnitToken mt2 = NumbersWithUnitToken.tryParse(mt.getEndToken().getNext().getNext(), addUnits, _not, false, false);
            if (mt2 != null) {
                NumbersWithUnitToken mt3 = null;
                if ((mt2.getWhitespacesAfterCount() < 2) && MeasureHelper.isMultChar(mt2.getEndToken().getNext())) 
                    mt3 = NumbersWithUnitToken.tryParse(mt2.getEndToken().getNext().getNext(), addUnits, false, false, false);
                if (mt3 == null) {
                    com.pullenti.ner.Token tt2 = mt2.getEndToken().getNext();
                    if (tt2 != null && !tt2.isWhitespaceBefore()) {
                        if (!tt2.isCharOf(",.;")) 
                            return null;
                    }
                }
                if (mt3 != null && mt3.units.size() > 0) {
                    if (mt2.units.size() == 0) 
                        mt2.units = mt3.units;
                }
                res.add(mt);
                if (mt2 != null) {
                    if (mt2.units.size() > 0 && mt.units.size() == 0) 
                        mt.units = mt2.units;
                    res.add(mt2);
                    if (mt3 != null) 
                        res.add(mt3);
                }
                return res;
            }
        }
        if ((!mt.isWhitespaceAfter() && MeasureHelper.isMultCharEnd(mt.getEndToken().getNext()) && (mt.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && mt.units.size() == 0) {
            String utxt = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(mt.getEndToken().getNext(), com.pullenti.ner.TextToken.class))).term;
            utxt = utxt.substring(0, 0 + utxt.length() - 1);
            java.util.ArrayList<com.pullenti.ner.core.Termin> terms = UnitsHelper.TERMINS.tryAttachStr(utxt, null);
            if (terms != null && terms.size() > 0) {
                mt.units.add(UnitToken._new1602(mt.getEndToken().getNext(), mt.getEndToken().getNext(), (Unit)com.pullenti.unisharp.Utils.cast(terms.get(0).tag, Unit.class)));
                mt.setEndToken(mt.getEndToken().getNext());
                java.util.ArrayList<NumbersWithUnitToken> res1 = tryParseMulti(mt.getEndToken().getNext(), addUnits, false, false, false);
                if (res1 != null) {
                    res1.add(0, mt);
                    return res1;
                }
            }
        }
        res.add(mt);
        return res;
    }

    /**
     * Попробовать выделить с указанной позиции
     * @param t 
     * @return 
     */
    public static NumbersWithUnitToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean canOmitNumber, boolean _not, boolean canBeNan) {
        if (t == null) 
            return null;
        NumbersWithUnitToken res = _tryParse(t, addUnits, false, canOmitNumber, canBeNan);
        if (res != null) 
            res.not = _not;
        return res;
    }

    public static com.pullenti.ner.Token _isMinOrMax(com.pullenti.ner.Token t, com.pullenti.unisharp.Outargwrapper<Integer> res) {
        if (t == null) 
            return null;
        if (t.isValue("МИНИМАЛЬНЫЙ", null) || t.isValue("МИНИМУМ", null) || t.isValue("MINIMUM", null)) {
            res.value = -1;
            return t;
        }
        if (t.isValue("MIN", null) || t.isValue("МИН", null)) {
            res.value = -1;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
            return t;
        }
        if (t.isValue("МАКСИМАЛЬНЫЙ", null) || t.isValue("МАКСИМУМ", null) || t.isValue("MAXIMUM", null)) {
            res.value = 1;
            return t;
        }
        if (t.isValue("MAX", null) || t.isValue("МАКС", null) || t.isValue("МАХ", null)) {
            res.value = 1;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
            return t;
        }
        if (t.isChar('(')) {
            t = _isMinOrMax(t.getNext(), res);
            if (t != null && t.getNext() != null && t.getNext().isChar(')')) 
                t = t.getNext();
            return t;
        }
        return null;
    }

    public static NumbersWithUnitToken _tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean second, boolean canOmitNumber, boolean canBeNan) {
        if (t == null) 
            return null;
        while (t != null) {
            if (t.isCommaAnd() || t.isValue("НО", null)) 
                t = t.getNext();
            else 
                break;
        }
        com.pullenti.ner.Token t0 = t;
        boolean _about = false;
        boolean hasKeyw = false;
        int minMax = 0;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapminMax1609 = new com.pullenti.unisharp.Outargwrapper<Integer>(minMax);
        com.pullenti.ner.Token ttt = _isMinOrMax(t, wrapminMax1609);
        minMax = (wrapminMax1609.value != null ? wrapminMax1609.value : 0);
        if (ttt != null) {
            t = ttt.getNext();
            if (t == null) 
                return null;
        }
        if (t == null) 
            return null;
        if (t.isChar('~') || t.isValue("ОКОЛО", null) || t.isValue("ПРИМЕРНО", null)) {
            t = t.getNext();
            _about = true;
            hasKeyw = true;
            if (t == null) 
                return null;
        }
        if (t0.isChar('(')) {
            NumbersWithUnitToken mt0 = _tryParse(t.getNext(), addUnits, false, false, false);
            if (mt0 != null && mt0.getEndToken().getNext() != null && mt0.getEndToken().getNext().isChar(')')) {
                if (second) {
                    if (mt0.fromVal != null && mt0.toVal != null && mt0.fromVal == (-mt0.toVal)) {
                    }
                    else 
                        return null;
                }
                mt0.setBeginToken(t0);
                mt0.setEndToken(mt0.getEndToken().getNext());
                java.util.ArrayList<UnitToken> uu = UnitToken.tryParseList(mt0.getEndToken().getNext(), addUnits, false);
                if (uu != null && mt0.units.size() == 0) {
                    mt0.units = uu;
                    mt0.setEndToken(uu.get(uu.size() - 1).getEndToken());
                }
                return mt0;
            }
        }
        boolean plusminus = false;
        boolean unitBefore = false;
        DiapTyp dty = DiapTyp.UNDEFINED;
        java.util.ArrayList<UnitToken> uni = null;
        com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            t = tok.getEndToken().getNext();
            dty = (DiapTyp)tok.termin.tag;
            hasKeyw = true;
            if (!tok.isWhitespaceAfter()) {
                if (t == null) 
                    return null;
                if (t.isCharOf(":")) {
                }
                else if (t instanceof com.pullenti.ner.NumberToken) {
                }
                else if (t.isComma() && t.getNext() != null && t.getNext().isValue("ЧЕМ", null)) {
                    t = t.getNext().getNext();
                    if (t != null && t.getMorph()._getClass().isPreposition()) 
                        t = t.getNext();
                }
                else 
                    return null;
            }
            if (t != null && t.isChar('(')) {
                uni = UnitToken.tryParseList(t.getNext(), addUnits, false);
                if (uni != null) {
                    t = uni.get(uni.size() - 1).getEndToken().getNext();
                    while (t != null) {
                        if (t.isCharOf("):")) 
                            t = t.getNext();
                        else 
                            break;
                    }
                    NumbersWithUnitToken mt0 = _tryParse(t, addUnits, false, canOmitNumber, false);
                    if (mt0 != null && mt0.units.size() == 0) {
                        mt0.setBeginToken(t0);
                        mt0.units = uni;
                        return mt0;
                    }
                }
            }
        }
        else if (t.isChar('<')) {
            dty = DiapTyp.LS;
            t = t.getNext();
            hasKeyw = true;
            if (t != null && t.isChar('=')) {
                t = t.getNext();
                dty = DiapTyp.LE;
            }
        }
        else if (t.isChar('>')) {
            dty = DiapTyp.GT;
            t = t.getNext();
            hasKeyw = true;
            if (t != null && t.isChar('=')) {
                t = t.getNext();
                dty = DiapTyp.GE;
            }
        }
        else if (t.isChar('≤')) {
            dty = DiapTyp.LE;
            hasKeyw = true;
            t = t.getNext();
        }
        else if (t.isChar('≥')) {
            dty = DiapTyp.GE;
            hasKeyw = true;
            t = t.getNext();
        }
        while (t != null && ((t.isChar(':') || t.isValue("ЧЕМ", null)))) {
            t = t.getNext();
        }
        if (t != null) {
            if (t.isChar('+') || t.isValue("ПЛЮС", null)) {
                t = t.getNext();
                if (t != null && !t.isWhitespaceBefore()) {
                    if (t.isHiphen()) {
                        t = t.getNext();
                        plusminus = true;
                    }
                    else if ((t.isCharOf("\\/") && t.getNext() != null && !t.isNewlineAfter()) && t.getNext().isHiphen()) {
                        t = t.getNext().getNext();
                        plusminus = true;
                    }
                }
            }
            else if (second && ((t.isCharOf("\\/÷…~")))) 
                t = t.getNext();
            else if ((t.isHiphen() && t == t0 && !second) && m_Termins.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                tok = m_Termins.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                t = tok.getEndToken().getNext();
                dty = (DiapTyp)tok.termin.tag;
            }
            else if (t.isHiphen() && t == t0 && ((t.isWhitespaceAfter() || second))) 
                t = t.getNext();
            else if (t.isChar('±')) {
                t = t.getNext();
                plusminus = true;
                hasKeyw = true;
            }
            else if ((second && t.isChar('.') && t.getNext() != null) && t.getNext().isChar('.')) {
                t = t.getNext().getNext();
                if (t != null && t.isChar('.')) 
                    t = t.getNext();
            }
        }
        com.pullenti.ner.NumberToken num = com.pullenti.ner.core.NumberHelper.tryParseRealNumber(t, true);
        if (num == null) {
            uni = UnitToken.tryParseList(t, addUnits, false);
            if (uni != null) {
                unitBefore = true;
                t = uni.get(uni.size() - 1).getEndToken().getNext();
                boolean delim = false;
                while (t != null) {
                    if (t.isCharOf(":,")) {
                        delim = true;
                        t = t.getNext();
                    }
                    else 
                        break;
                }
                if (!delim) {
                    if (t == null) {
                        if (hasKeyw && canBeNan) {
                        }
                        else 
                            return null;
                    }
                    else if (!t.isWhitespaceBefore()) 
                        return null;
                    if (t.getNext() != null && t.isHiphen() && t.isWhitespaceAfter()) {
                        delim = true;
                        t = t.getNext();
                    }
                }
                num = com.pullenti.ner.core.NumberHelper.tryParseRealNumber(t, true);
            }
        }
        NumbersWithUnitToken res = null;
        double rval = 0.0;
        if (num == null) {
            com.pullenti.ner.core.TerminToken tt = m_Spec.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tt != null) {
                rval = (double)tt.termin.tag;
                String unam = tt.termin.tag2.toString();
                for (Unit u : UnitsHelper.UNITS) {
                    if (com.pullenti.unisharp.Utils.stringsEq(u.fullnameCyr, unam)) {
                        uni = new java.util.ArrayList<UnitToken>();
                        uni.add(UnitToken._new1602(t, t, u));
                        break;
                    }
                }
                if (uni == null) 
                    return null;
                res = _new1604(t0, tt.getEndToken(), _about);
                t = tt.getEndToken().getNext();
            }
            else {
                if (!canOmitNumber && !hasKeyw && !canBeNan) 
                    return null;
                if ((uni != null && uni.size() == 1 && uni.get(0).getBeginToken() == uni.get(0).getEndToken()) && uni.get(0).getLengthChar() > 3) {
                    rval = 1.0;
                    res = _new1604(t0, uni.get(uni.size() - 1).getEndToken(), _about);
                    t = res.getEndToken().getNext();
                }
                else if (hasKeyw && canBeNan) {
                    rval = Double.NaN;
                    res = _new1604(t0, t0, _about);
                    if (t != null) 
                        res.setEndToken(t.getPrevious());
                    else 
                        for (t = t0; t != null; t = t.getNext()) {
                            res.setEndToken(t);
                        }
                }
                else 
                    return null;
            }
        }
        else {
            if ((t == t0 && t0.isHiphen() && !t.isWhitespaceBefore()) && !t.isWhitespaceAfter() && (num.getRealValue() < 0)) 
                return null;
            if (t == t0 && (t instanceof com.pullenti.ner.NumberToken) && t.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.TextToken nn = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getEndToken(), com.pullenti.ner.TextToken.class);
                if (nn == null) 
                    return null;
                String norm = nn.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                if ((norm.endsWith("Ь") || com.pullenti.unisharp.Utils.stringsEq(norm, "ЧЕТЫРЕ") || com.pullenti.unisharp.Utils.stringsEq(norm, "ТРИ")) || com.pullenti.unisharp.Utils.stringsEq(norm, "ДВА")) {
                }
                else {
                    com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.Morphology.getWordBaseInfo("КОКО" + nn.term, null, false, false);
                    if (mi._getClass().isAdjective()) 
                        return null;
                }
            }
            t = num.getEndToken().getNext();
            res = _new1604(t0, num.getEndToken(), _about);
            rval = num.getRealValue();
        }
        if (uni == null) {
            uni = UnitToken.tryParseList(t, addUnits, false);
            if (uni != null) {
                if ((plusminus && second && uni.size() == 1) && uni.get(0).unit == UnitsHelper.UPERCENT) {
                    res.setEndToken(uni.get(uni.size() - 1).getEndToken());
                    res.plusMinusPercent = true;
                    com.pullenti.ner.Token tt1 = uni.get(0).getEndToken().getNext();
                    uni = UnitToken.tryParseList(tt1, addUnits, false);
                    if (uni != null) {
                        res.units = uni;
                        res.setEndToken(uni.get(uni.size() - 1).getEndToken());
                    }
                }
                else {
                    res.units = uni;
                    res.setEndToken(uni.get(uni.size() - 1).getEndToken());
                }
                t = res.getEndToken().getNext();
            }
        }
        else {
            res.units = uni;
            if (uni.size() > 1) {
                java.util.ArrayList<UnitToken> uni1 = UnitToken.tryParseList(t, addUnits, false);
                if (((uni1 != null && uni1.get(0).unit == uni.get(0).unit && (uni1.size() < uni.size())) && uni.get(uni1.size()).pow == -1 && uni1.get(uni1.size() - 1).getEndToken().getNext() != null) && uni1.get(uni1.size() - 1).getEndToken().getNext().isCharOf("/\\")) {
                    NumbersWithUnitToken num2 = _tryParse(uni1.get(uni1.size() - 1).getEndToken().getNext().getNext(), addUnits, false, false, false);
                    if (num2 != null && num2.units != null && num2.units.get(0).unit == uni.get(uni1.size()).unit) {
                        res.units = uni1;
                        res.divNum = num2;
                        res.setEndToken(num2.getEndToken());
                    }
                }
            }
        }
        if (dty != DiapTyp.UNDEFINED) {
            if (dty == DiapTyp.GE || dty == DiapTyp.FROM) {
                res.fromInclude = true;
                res.fromVal = rval;
            }
            else if (dty == DiapTyp.GT) {
                res.fromInclude = false;
                res.fromVal = rval;
            }
            else if (dty == DiapTyp.LE || dty == DiapTyp.TO) {
                res.toInclude = true;
                res.toVal = rval;
            }
            else if (dty == DiapTyp.LS) {
                res.toInclude = false;
                res.toVal = rval;
            }
        }
        boolean isSecondMax = false;
        if (!second) {
            int iii = 0;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapiii1608 = new com.pullenti.unisharp.Outargwrapper<Integer>(iii);
            ttt = _isMinOrMax(t, wrapiii1608);
            iii = (wrapiii1608.value != null ? wrapiii1608.value : 0);
            if (ttt != null && iii > 0) {
                isSecondMax = true;
                t = ttt.getNext();
            }
        }
        NumbersWithUnitToken _next = (second || plusminus || ((t != null && t.isNewlineBefore())) ? null : _tryParse(t, addUnits, true, false, canBeNan));
        if (_next != null && ((_next.toVal != null || _next.singleVal != null)) && _next.fromVal == null) {
            if ((((_next.getBeginToken().isChar('+') && _next.singleVal != null && !Double.isNaN(_next.singleVal)) && _next.getEndToken().getNext() != null && _next.getEndToken().getNext().isCharOf("\\/")) && _next.getEndToken().getNext().getNext() != null && _next.getEndToken().getNext().getNext().isHiphen()) && !hasKeyw && !Double.isNaN(rval)) {
                NumbersWithUnitToken next2 = _tryParse(_next.getEndToken().getNext().getNext().getNext(), addUnits, true, false, false);
                if (next2 != null && next2.singleVal != null && !Double.isNaN(next2.singleVal)) {
                    res.fromVal = rval - next2.singleVal;
                    res.fromInclude = true;
                    res.toVal = rval + _next.singleVal;
                    res.toInclude = true;
                    if (next2.units != null && res.units.size() == 0) 
                        res.units = next2.units;
                    res.setEndToken(next2.getEndToken());
                    return res;
                }
            }
            if (_next.units.size() > 0) {
                if (res.units.size() == 0) 
                    res.units = _next.units;
                else if (!UnitToken.canBeEquals(res.units, _next.units)) 
                    _next = null;
            }
            else if (res.units.size() > 0 && !unitBefore && !_next.plusMinusPercent) 
                _next = null;
            if (_next != null) 
                res.setEndToken(_next.getEndToken());
            if (_next != null && _next.toVal != null) {
                res.toVal = _next.toVal;
                res.toInclude = _next.toInclude;
            }
            else if (_next != null && _next.singleVal != null) {
                if (_next.getBeginToken().isCharOf("/\\")) {
                    res.divNum = _next;
                    res.singleVal = rval;
                    return res;
                }
                else if (_next.plusMinusPercent) {
                    res.singleVal = rval;
                    res.plusMinus = _next.singleVal;
                    res.plusMinusPercent = true;
                    res.toInclude = true;
                }
                else {
                    res.toVal = _next.singleVal;
                    res.toInclude = true;
                }
            }
            if (_next != null) {
                if (res.fromVal == null) {
                    res.fromVal = rval;
                    res.fromInclude = true;
                }
                return res;
            }
        }
        else if ((_next != null && _next.fromVal != null && _next.toVal != null) && _next.toVal == (-_next.fromVal)) {
            if (_next.units.size() == 1 && _next.units.get(0).unit == UnitsHelper.UPERCENT && res.units.size() > 0) {
                res.singleVal = rval;
                res.plusMinus = _next.toVal;
                res.plusMinusPercent = true;
                res.setEndToken(_next.getEndToken());
                return res;
            }
            if (_next.units.size() == 0) {
                res.singleVal = rval;
                res.plusMinus = _next.toVal;
                res.setEndToken(_next.getEndToken());
                return res;
            }
            res.fromVal = _next.fromVal + rval;
            res.fromInclude = true;
            res.toVal = _next.toVal + rval;
            res.toInclude = true;
            res.setEndToken(_next.getEndToken());
            if (_next.units.size() > 0) 
                res.units = _next.units;
            return res;
        }
        if (dty == DiapTyp.UNDEFINED) {
            if (plusminus && ((!res.plusMinusPercent || !second))) {
                res.fromInclude = true;
                res.fromVal = -rval;
                res.toInclude = true;
                res.toVal = rval;
            }
            else {
                res.singleVal = rval;
                res.plusMinusPercent = plusminus;
            }
        }
        return res;
    }

    /**
     * Это распознавание написаний ГхШхВ
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.MetaToken _tryParseWHL(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (t.isCharOf(":-")) {
            com.pullenti.ner.MetaToken re0 = _tryParseWHL(t.getNext());
            if (re0 != null) 
                return re0;
        }
        if (t.isCharOf("(")) {
            com.pullenti.ner.MetaToken re0 = _tryParseWHL(t.getNext());
            if (re0 != null) {
                if (re0.getEndToken().getNext() != null && re0.getEndToken().getNext().isChar(')')) {
                    re0.setBeginToken(t);
                    re0.setEndToken(re0.getEndToken().getNext());
                    return re0;
                }
            }
        }
        String txt = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        java.util.ArrayList<String> nams = null;
        if (txt.length() == 5 && txt.charAt(1) == 'Х' && txt.charAt(3) == 'Х') {
            nams = new java.util.ArrayList<String>();
            for (int i = 0; i < 3; i++) {
                char ch = txt.charAt(i * 2);
                if (ch == 'Г') 
                    nams.add("ГЛУБИНА");
                else if (ch == 'В') 
                    nams.add("ВЫСОТА");
                else if (ch == 'Ш') 
                    nams.add("ШИРИНА");
                else if (ch == 'Д') 
                    nams.add("ДЛИНА");
                else 
                    return null;
            }
            return com.pullenti.ner.MetaToken._new828(t, t, nams);
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        for (; t != null; t = t.getNext()) {
            if (!((t instanceof com.pullenti.ner.TextToken)) || ((t.getWhitespacesBeforeCount() > 1 && t != t0))) 
                break;
            String term = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            String nam = null;
            if ((t.isValue("ДЛИНА", null) || t.isValue("ДЛИННА", null) || com.pullenti.unisharp.Utils.stringsEq(term, "Д")) || com.pullenti.unisharp.Utils.stringsEq(term, "ДЛ") || com.pullenti.unisharp.Utils.stringsEq(term, "ДЛИН")) 
                nam = "ДЛИНА";
            else if ((t.isValue("ШИРИНА", null) || t.isValue("ШИРОТА", null) || com.pullenti.unisharp.Utils.stringsEq(term, "Ш")) || com.pullenti.unisharp.Utils.stringsEq(term, "ШИР") || com.pullenti.unisharp.Utils.stringsEq(term, "ШИРИН")) 
                nam = "ШИРИНА";
            else if ((t.isValue("ГЛУБИНА", null) || com.pullenti.unisharp.Utils.stringsEq(term, "Г") || com.pullenti.unisharp.Utils.stringsEq(term, "ГЛ")) || com.pullenti.unisharp.Utils.stringsEq(term, "ГЛУБ")) 
                nam = "ГЛУБИНА";
            else if (t.isValue("ВЫСОТА", null) || com.pullenti.unisharp.Utils.stringsEq(term, "В") || com.pullenti.unisharp.Utils.stringsEq(term, "ВЫС")) 
                nam = "ВЫСОТА";
            else 
                break;
            if (nams == null) 
                nams = new java.util.ArrayList<String>();
            nams.add(nam);
            t1 = t;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t1 = (t = t.getNext());
            if (t.getNext() == null) 
                break;
            if (MeasureHelper.isMultChar(t.getNext()) || t.getNext().isComma() || t.getNext().isCharOf("\\/")) 
                t = t.getNext();
        }
        if (nams == null || (nams.size() < 2)) 
            return null;
        return com.pullenti.ner.MetaToken._new828(t0, t1, nams);
    }

    public static com.pullenti.ner.core.TerminCollection m_Termins;

    public static com.pullenti.ner.core.TerminCollection m_Spec;

    public static void initialize() {
        if (m_Termins != null) 
            return;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new119("НЕ МЕНЕЕ", DiapTyp.GE);
        t.addVariant("НЕ МЕНЬШЕ", false);
        t.addVariant("НЕ КОРОЧЕ", false);
        t.addVariant("НЕ МЕДЛЕННЕЕ", false);
        t.addVariant("НЕ НИЖЕ", false);
        t.addVariant("НЕ МЕНЕ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("МЕНЕЕ", DiapTyp.LS);
        t.addVariant("МЕНЬШЕ", false);
        t.addVariant("МЕНЕ", false);
        t.addVariant("КОРОЧЕ", false);
        t.addVariant("МЕДЛЕННЕЕ", false);
        t.addVariant("НИЖЕ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("НЕ БОЛЕЕ", DiapTyp.LE);
        t.addVariant("НЕ БОЛЬШЕ", false);
        t.addVariant("НЕ БОЛЕ", false);
        t.addVariant("НЕ ДЛИННЕЕ", false);
        t.addVariant("НЕ БЫСТРЕЕ", false);
        t.addVariant("НЕ ВЫШЕ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("БОЛЕЕ", DiapTyp.GT);
        t.addVariant("БОЛЬШЕ", false);
        t.addVariant("ДЛИННЕЕ", false);
        t.addVariant("БЫСТРЕЕ", false);
        t.addVariant("БОЛЕ", false);
        t.addVariant("ГЛУБЖЕ", false);
        t.addVariant("ВЫШЕ", false);
        t.addVariant("СВЫШЕ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("ОТ", DiapTyp.FROM);
        t.addVariant("С", false);
        t.addVariant("C", false);
        t.addVariant("НАЧИНАЯ С", false);
        t.addVariant("НАЧИНАЯ ОТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("ДО", DiapTyp.TO);
        t.addVariant("ПО", false);
        t.addVariant("ЗАКАНЧИВАЯ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new119("НЕ ХУЖЕ", DiapTyp.UNDEFINED);
        m_Termins.add(t);
        m_Spec = new com.pullenti.ner.core.TerminCollection();
        t = com.pullenti.ner.core.Termin._new121("ПОЛЛИТРА", 0.5, "литр");
        t.addVariant("ПОЛУЛИТРА", false);
        m_Spec.add(t);
        t = com.pullenti.ner.core.Termin._new121("ПОЛКИЛО", 0.5, "килограмм");
        t.addVariant("ПОЛКИЛОГРАММА", false);
        m_Spec.add(t);
        t = com.pullenti.ner.core.Termin._new121("ПОЛМЕТРА", 0.5, "метр");
        t.addVariant("ПОЛУМЕТРА", false);
        m_Spec.add(t);
        t = com.pullenti.ner.core.Termin._new121("ПОЛТОННЫ", 0.5, "тонна");
        t.addVariant("ПОЛУТОННЫ", false);
        m_Spec.add(t);
        m_Spec.add(t);
    }

    public static NumbersWithUnitToken _new1595(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Double _arg3) {
        NumbersWithUnitToken res = new NumbersWithUnitToken(_arg1, _arg2);
        res.singleVal = _arg3;
        return res;
    }

    public static NumbersWithUnitToken _new1604(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        NumbersWithUnitToken res = new NumbersWithUnitToken(_arg1, _arg2);
        res.about = _arg3;
        return res;
    }

    public static class DiapTyp implements Comparable<DiapTyp> {
    
        public static final DiapTyp UNDEFINED; // 0
    
        public static final DiapTyp LS; // 1
    
        public static final DiapTyp LE; // 2
    
        public static final DiapTyp GT; // 3
    
        public static final DiapTyp GE; // 4
    
        public static final DiapTyp FROM; // 5
    
        public static final DiapTyp TO; // 6
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DiapTyp(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(DiapTyp v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DiapTyp> mapIntToEnum; 
        private static java.util.HashMap<String, DiapTyp> mapStringToEnum; 
        private static DiapTyp[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static DiapTyp of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DiapTyp item = new DiapTyp(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DiapTyp of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static DiapTyp[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, DiapTyp>();
            mapStringToEnum = new java.util.HashMap<String, DiapTyp>();
            UNDEFINED = new DiapTyp(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            LS = new DiapTyp(1, "LS");
            mapIntToEnum.put(LS.value(), LS);
            mapStringToEnum.put(LS.m_str.toUpperCase(), LS);
            LE = new DiapTyp(2, "LE");
            mapIntToEnum.put(LE.value(), LE);
            mapStringToEnum.put(LE.m_str.toUpperCase(), LE);
            GT = new DiapTyp(3, "GT");
            mapIntToEnum.put(GT.value(), GT);
            mapStringToEnum.put(GT.m_str.toUpperCase(), GT);
            GE = new DiapTyp(4, "GE");
            mapIntToEnum.put(GE.value(), GE);
            mapStringToEnum.put(GE.m_str.toUpperCase(), GE);
            FROM = new DiapTyp(5, "FROM");
            mapIntToEnum.put(FROM.value(), FROM);
            mapStringToEnum.put(FROM.m_str.toUpperCase(), FROM);
            TO = new DiapTyp(6, "TO");
            mapIntToEnum.put(TO.value(), TO);
            mapStringToEnum.put(TO.m_str.toUpperCase(), TO);
            java.util.Collection<DiapTyp> col = mapIntToEnum.values();
            m_Values = new DiapTyp[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public NumbersWithUnitToken() {
        super();
    }
    public static NumbersWithUnitToken _globalInstance;
    
    static {
        _globalInstance = new NumbersWithUnitToken(); 
    }
}
