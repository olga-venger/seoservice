/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.named.internal;

public class NamedItemToken extends com.pullenti.ner.MetaToken {

    public NamedItemToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public com.pullenti.ner.named.NamedEntityKind kind = com.pullenti.ner.named.NamedEntityKind.UNDEFINED;

    public String nameValue;

    public String typeValue;

    public com.pullenti.ner.Referent ref;

    public boolean isWellknown;

    public boolean isInBracket;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (kind != com.pullenti.ner.named.NamedEntityKind.UNDEFINED) 
            res.append(" [").append(kind.toString()).append("]");
        if (isWellknown) 
            res.append(" (!)");
        if (isInBracket) 
            res.append(" [br]");
        if (typeValue != null) 
            res.append(" ").append(typeValue);
        if (nameValue != null) 
            res.append(" \"").append(nameValue).append("\"");
        if (ref != null) 
            res.append(" -> ").append(ref.toString());
        return res.toString();
    }

    public static java.util.ArrayList<NamedItemToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locOnto) {
        NamedItemToken ne = tryParse(t, locOnto);
        if (ne == null) 
            return null;
        java.util.ArrayList<NamedItemToken> res = new java.util.ArrayList<NamedItemToken>();
        res.add(ne);
        for (t = ne.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.getWhitespacesBeforeCount() > 2) 
                break;
            ne = tryParse(t, locOnto);
            if (ne == null) 
                break;
            if (t.isValue("НЕТ", null)) 
                break;
            res.add(ne);
            t = ne.getEndToken();
        }
        return res;
    }

    public static NamedItemToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locOnto) {
        if (t == null) 
            return null;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.Referent r = t.getReferent();
            if ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PERSON") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PERSONPROPERTY") || (r instanceof com.pullenti.ner.geo.GeoReferent)) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) 
                return _new1721(t, t, r, t.getMorph());
            return null;
        }
        com.pullenti.ner.core.TerminToken typ = m_Types.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        com.pullenti.ner.core.TerminToken nam = m_Names.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (typ != null) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                return null;
            NamedItemToken res = _new1722(typ.getBeginToken(), typ.getEndToken(), typ.getMorph(), typ.chars);
            res.kind = (com.pullenti.ner.named.NamedEntityKind)typ.termin.tag;
            res.typeValue = typ.termin.getCanonicText();
            if ((nam != null && nam.getEndToken() == typ.getEndToken() && !t.chars.isAllLower()) && ((com.pullenti.ner.named.NamedEntityKind)nam.termin.tag) == res.kind) {
                res.nameValue = nam.termin.getCanonicText();
                res.isWellknown = true;
            }
            return res;
        }
        if (nam != null) {
            if (nam.getBeginToken().chars.isAllLower()) 
                return null;
            NamedItemToken res = _new1722(nam.getBeginToken(), nam.getEndToken(), nam.getMorph(), nam.chars);
            res.kind = (com.pullenti.ner.named.NamedEntityKind)nam.termin.tag;
            res.nameValue = nam.termin.getCanonicText();
            boolean ok = true;
            if (!t.isWhitespaceBefore() && t.getPrevious() != null) 
                ok = false;
            else if (!t.isWhitespaceAfter() && t.getNext() != null) {
                if (t.getNext().isCharOf(",.;!?") && t.getNext().isWhitespaceAfter()) {
                }
                else 
                    ok = false;
            }
            if (ok) {
                res.isWellknown = true;
                res.typeValue = (String)com.pullenti.unisharp.Utils.cast(nam.termin.tag2, String.class);
            }
            return res;
        }
        com.pullenti.ner.MetaToken adj = com.pullenti.ner.geo.internal.MiscLocationHelper.tryAttachNordWest(t);
        if (adj != null) {
            if (adj.getMorph()._getClass().isNoun()) {
                if (adj.getEndToken().isValue("ВОСТОК", null)) {
                    if (adj.getBeginToken() == adj.getEndToken()) 
                        return null;
                    NamedItemToken re = _new1724(t, adj.getEndToken(), adj.getMorph());
                    re.kind = com.pullenti.ner.named.NamedEntityKind.LOCATION;
                    re.nameValue = com.pullenti.ner.core.MiscHelper.getTextValue(t, adj.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    re.isWellknown = true;
                    return re;
                }
                return null;
            }
            if (adj.getWhitespacesAfterCount() > 2) 
                return null;
            if ((adj.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && (adj.getEndToken().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                NamedItemToken re = _new1724(t, adj.getEndToken().getNext(), adj.getEndToken().getNext().getMorph());
                re.kind = com.pullenti.ner.named.NamedEntityKind.LOCATION;
                re.nameValue = com.pullenti.ner.core.MiscHelper.getTextValue(t, adj.getEndToken().getNext(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                re.isWellknown = true;
                re.ref = adj.getEndToken().getNext().getReferent();
                return re;
            }
            NamedItemToken res = tryParse(adj.getEndToken().getNext(), locOnto);
            if (res != null && res.kind == com.pullenti.ner.named.NamedEntityKind.LOCATION) {
                String s = adj.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, res.getMorph().getGender(), false);
                if (s != null) {
                    if (res.nameValue == null) 
                        res.nameValue = s.toUpperCase();
                    else {
                        res.nameValue = (s.toUpperCase() + " " + res.nameValue);
                        res.typeValue = null;
                    }
                    res.setBeginToken(t);
                    res.chars = t.chars;
                    res.isWellknown = true;
                    return res;
                }
            }
        }
        if (t.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.adjectives.size() > 0) {
                NamedItemToken test = tryParse(npt.noun.getBeginToken(), locOnto);
                if (test != null && test.getEndToken() == npt.getEndToken() && test.typeValue != null) {
                    test.setBeginToken(t);
                    StringBuilder tmp = new StringBuilder();
                    for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                        String s = a.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, test.getMorph().getGender(), false);
                        if (tmp.length() > 0) 
                            tmp.append(' ');
                        tmp.append(s);
                    }
                    test.nameValue = tmp.toString();
                    test.chars = t.chars;
                    if (test.kind == com.pullenti.ner.named.NamedEntityKind.LOCATION) 
                        test.isWellknown = true;
                    return test;
                }
            }
        }
        if ((com.pullenti.ner.core.BracketHelper.isBracket(t, true) && t.getNext() != null && t.getNext().chars.isLetter()) && !t.getNext().chars.isAllLower()) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                NamedItemToken res = new NamedItemToken(t, br.getEndToken());
                res.isInBracket = true;
                res.nameValue = com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                nam = m_Names.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (nam != null && nam.getEndToken() == br.getEndToken().getPrevious()) {
                    res.kind = (com.pullenti.ner.named.NamedEntityKind)nam.termin.tag;
                    res.isWellknown = true;
                    res.nameValue = nam.termin.getCanonicText();
                }
                return res;
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) && t.getLengthChar() > 2) {
            NamedItemToken res = _new1724(t, t, t.getMorph());
            String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (str.endsWith("О") || str.endsWith("И") || str.endsWith("Ы")) 
                res.nameValue = str;
            else 
                res.nameValue = t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
            res.chars = t.chars;
            if (((!t.isWhitespaceAfter() && t.getNext() != null && t.getNext().isHiphen()) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().getNext().isWhitespaceAfter()) && t.chars.isCyrillicLetter() == t.getNext().getNext().chars.isCyrillicLetter()) {
                t = res.setEndToken(t.getNext().getNext());
                res.nameValue = (res.nameValue + "-" + t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false));
            }
            return res;
        }
        return null;
    }

    public static void initialize() {
        if (m_Types != null) 
            return;
        m_Types = new com.pullenti.ner.core.TerminCollection();
        m_Names = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        for (String s : new String[] {"ПЛАНЕТА", "ЗВЕЗДА", "КОМЕТА", "МЕТЕОРИТ", "СОЗВЕЗДИЕ", "ГАЛАКТИКА"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.PLANET;
            m_Types.add(t);
        }
        for (String s : new String[] {"СОЛНЦЕ", "МЕРКУРИЙ", "ВЕНЕРА", "ЗЕМЛЯ", "МАРС", "ЮПИТЕР", "САТУРН", "УРАН", "НЕПТУН", "ПЛУТОН", "ЛУНА", "ДЕЙМОС", "ФОБОС", "Ио", "Ганимед", "Каллисто"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s.toUpperCase(), null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.PLANET;
            m_Names.add(t);
        }
        for (String s : new String[] {"РЕКА", "ОЗЕРО", "МОРЕ", "ОКЕАН", "ЗАЛИВ", "ПРОЛИВ", "ПОБЕРЕЖЬЕ", "КОНТИНЕНТ", "ОСТРОВ", "ПОЛУОСТРОВ", "МЫС", "ГОРА", "ГОРНЫЙ ХРЕБЕТ", "ПЕРЕВАЛ", "ЛЕС", "САД", "ЗАПОВЕДНИК", "ЗАКАЗНИК", "ДОЛИНА", "УЩЕЛЬЕ", "РАВНИНА", "БЕРЕГ"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.LOCATION;
            m_Types.add(t);
        }
        for (String s : new String[] {"ТИХИЙ", "АТЛАНТИЧЕСКИЙ", "ИНДИЙСКИЙ", "СЕВЕРО-ЛЕДОВИТЫЙ"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.LOCATION;
            t.tag2 = "океан";
            m_Names.add(t);
        }
        for (String s : new String[] {"ЕВРАЗИЯ", "АФРИКА", "АМЕРИКА", "АВСТРАЛИЯ", "АНТАРКТИДА"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.LOCATION;
            t.tag2 = "континент";
            m_Names.add(t);
        }
        for (String s : new String[] {"ВОЛГА", "НЕВА", "АМУР", "ОБЪ", "АНГАРА", "ЛЕНА", "ИРТЫШ", "ДНЕПР", "ДОН", "ДНЕСТР", "РЕЙН", "АМУДАРЬЯ", "СЫРДАРЬЯ", "ТИГР", "ЕВФРАТ", "ИОРДАН", "МИССИСИПИ", "АМАЗОНКА", "ТЕМЗА", "СЕНА", "НИЛ", "ЯНЦЗЫ", "ХУАНХЭ", "ПАРАНА", "МЕКОНГ", "МАККЕНЗИ", "НИГЕР", "ЕНИСЕЙ", "МУРРЕЙ", "САЛУИН", "ИНД", "РИО-ГРАНДЕ", "БРАХМАПУТРА", "ДАРЛИНГ", "ДУНАЙ", "ЮКОН", "ГАНГ", "МАРРАМБИДЖИ", "ЗАМБЕЗИ", "ТОКАНТИС", "ОРИНОКО", "СИЦЗЯН", "КОЛЫМА", "КАМА", "ОКА", "ЭЛЬЮА", "ВИСЛА", "ДАУГАВА", "ЗАПАДНАЯ ДВИНА", "НЕМАН", "МЕЗЕНЬ", "КУБАНЬ", "ЮЖНЫЙ БУГ"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.LOCATION;
            t.tag2 = "река";
            m_Names.add(t);
        }
        for (String s : new String[] {"ЕВРОПА", "АЗИЯ", "АРКТИКА", "КАВКАЗ", "ПРИБАЛТИКА", "СИБИРЬ", "ЗАПОЛЯРЬЕ", "ЧУКОТКА", "ПРИБАЛТИКА", "БАЛКАНЫ", "СКАНДИНАВИЯ", "ОКЕАНИЯ", "АЛЯСКА", "УРАЛ", "ПОВОЛЖЬЕ", "ПРИМОРЬЕ", "КУРИЛЫ", "ТИБЕТ", "ГИМАЛАИ", "АЛЬПЫ", "САХАРА", "ГОБИ", "СИНАЙ", "БАЙКОНУР", "ЧЕРНОБЫЛЬ", "САДОВОЕ КОЛЬЦО", "СТАРЫЙ ГОРОД"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.LOCATION;
            m_Names.add(t);
        }
        for (String s : new String[] {"ПАМЯТНИК", "МОНУМЕНТ", "МЕМОРИАЛ", "БЮСТ", "ОБЕЛИСК"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.MONUMENT;
            m_Types.add(t);
        }
        for (String s : new String[] {"ДВОРЕЦ", "КРЕМЛЬ", "ЗАМОК", "УСАДЬБА", "ДОМ", "ЗДАНИЕ", "ШТАБ-КВАРТИРА", "ЖЕЛЕЗНОДОРОЖНЫЙ ВОКЗАЛ", "ВОКЗАЛ", "АВТОВОКЗАЛ", "АЭРОПОРТ", "АЭРОДРОМ"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.BUILDING;
            m_Types.add(t);
        }
        for (String s : new String[] {"КРЕМЛЬ", "КАПИТОЛИЙ", "БЕЛЫЙ ДОМ"}) {
            t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(s, null);
            t.tag = com.pullenti.ner.named.NamedEntityKind.BUILDING;
            m_Names.add(t);
        }
        t = com.pullenti.ner.core.Termin._new119("МЕЖДУНАРОДНАЯ КОСМИЧЕСКАЯ СТАНЦИЯ", com.pullenti.ner.named.NamedEntityKind.BUILDING);
        t.acronym = "МКС";
        m_Names.add(t);
    }

    private static com.pullenti.ner.core.TerminCollection m_Types;

    private static com.pullenti.ner.core.TerminCollection m_Names;

    public static NamedItemToken _new1721(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Referent _arg3, com.pullenti.ner.MorphCollection _arg4) {
        NamedItemToken res = new NamedItemToken(_arg1, _arg2);
        res.ref = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static NamedItemToken _new1722(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, com.pullenti.morph.CharsInfo _arg4) {
        NamedItemToken res = new NamedItemToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.chars = _arg4;
        return res;
    }

    public static NamedItemToken _new1724(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        NamedItemToken res = new NamedItemToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public NamedItemToken() {
        super();
    }
}
