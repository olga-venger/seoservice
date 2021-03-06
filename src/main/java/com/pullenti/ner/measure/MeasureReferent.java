/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure;

/**
 * Величина или диапазон величин, измеряемая в некоторых единицах
 */
public class MeasureReferent extends com.pullenti.ner.Referent {

    public MeasureReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.measure.internal.MeasureMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "MEASURE";

    public static final String ATTR_TEMPLATE = "TEMPLATE";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_UNIT = "UNIT";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_KIND = "KIND";

    /**
     * [Get] Шаблон для значений, например, [1..2], 1x2, 1 ]..1]
     */
    public String getTemplate() {
        return (String)com.pullenti.unisharp.Utils.notnull(this.getStringValue(ATTR_TEMPLATE), "1");
    }

    /**
     * [Set] Шаблон для значений, например, [1..2], 1x2, 1 ]..1]
     */
    public String setTemplate(String value) {
        this.addSlot(ATTR_TEMPLATE, value, true, 0);
        return value;
    }


    public java.util.ArrayList<Double> getDoubleValues() {
        java.util.ArrayList<Double> res = new java.util.ArrayList<Double>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE) && (s.getValue() instanceof String)) {
                double d;
                com.pullenti.unisharp.Outargwrapper<Double> wrapd1710 = new com.pullenti.unisharp.Outargwrapper<Double>();
                boolean inoutres1711 = com.pullenti.ner.measure.internal.MeasureHelper.tryParseDouble((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), wrapd1710);
                d = (wrapd1710.value != null ? wrapd1710.value : 0);
                if (inoutres1711) 
                    res.add(d);
            }
        }
        return res;
    }


    public void addValue(double d) {
        this.addSlot(ATTR_VALUE, com.pullenti.ner.core.NumberHelper.doubleToString(d), false, 0);
    }

    public java.util.ArrayList<UnitReferent> getUnits() {
        java.util.ArrayList<UnitReferent> res = new java.util.ArrayList<UnitReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_UNIT) && (s.getValue() instanceof UnitReferent)) 
                res.add((UnitReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), UnitReferent.class));
        }
        return res;
    }


    public MeasureKind getKind() {
        String str = this.getStringValue(ATTR_KIND);
        if (str == null) 
            return MeasureKind.UNDEFINED;
        try {
            return (MeasureKind)MeasureKind.of(str);
        } catch (Exception ex1712) {
        }
        return MeasureKind.UNDEFINED;
    }

    public MeasureKind setKind(MeasureKind value) {
        if (value != MeasureKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString().toUpperCase(), true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder(getTemplate());
        java.util.ArrayList<String> vals = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE)) {
                if (s.getValue() instanceof String) {
                    String val = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                    if (com.pullenti.unisharp.Utils.stringsEq(val, "NaN")) 
                        val = "?";
                    vals.add(val);
                }
                else if (s.getValue() instanceof com.pullenti.ner.Referent) 
                    vals.add((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            }
        }
        for (int i = res.length() - 1; i >= 0; i--) {
            char ch = res.charAt(i);
            if (!Character.isDigit(ch)) 
                continue;
            int j = (((int)ch) - ((int)'1'));
            if ((j < 0) || j >= vals.size()) 
                continue;
            res.delete(i, i + 1);
            res.insert(i, vals.get(j));
        }
        this.outUnits(res, lang);
        if (!shortVariant) {
            String nam = this.getStringValue(ATTR_NAME);
            if (nam != null) 
                res.append(" - ").append(nam);
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof MeasureReferent)) 
                    res.append(" / ").append((((MeasureReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), MeasureReferent.class))).toString(true, lang, 0));
            }
            MeasureKind ki = getKind();
            if (ki != MeasureKind.UNDEFINED) 
                res.append(" (").append(ki.toString().toUpperCase()).append(")");
        }
        return res.toString();
    }

    public void outUnits(StringBuilder res, com.pullenti.morph.MorphLang lang) {
        java.util.ArrayList<UnitReferent> uu = getUnits();
        if (uu.size() == 0) 
            return;
        res.append(uu.get(0).toString(true, lang, 0));
        for (int i = 1; i < uu.size(); i++) {
            String pow = uu.get(i).getStringValue(UnitReferent.ATTR_POW);
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(pow) && pow.charAt(0) == '-') {
                res.append("/").append(uu.get(i).toString(true, lang, 1));
                if (com.pullenti.unisharp.Utils.stringsNe(pow, "-1")) 
                    res.append("<").append(pow.substring(1)).append(">");
            }
            else 
                res.append("*").append(uu.get(i).toString(true, lang, 0));
        }
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        MeasureReferent mr = (MeasureReferent)com.pullenti.unisharp.Utils.cast(obj, MeasureReferent.class);
        if (mr == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTemplate(), mr.getTemplate())) 
            return false;
        java.util.ArrayList<String> vals1 = this.getStringValues(ATTR_VALUE);
        java.util.ArrayList<String> vals2 = mr.getStringValues(ATTR_VALUE);
        if (vals1.size() != vals2.size()) 
            return false;
        for (int i = 0; i < vals2.size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsNe(vals1.get(i), vals2.get(i))) 
                return false;
        }
        java.util.ArrayList<UnitReferent> units1 = getUnits();
        java.util.ArrayList<UnitReferent> units2 = mr.getUnits();
        if (units1.size() != units2.size()) 
            return false;
        for (int i = 0; i < units2.size(); i++) {
            if (units1.get(i) != units2.get(i)) 
                return false;
        }
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (mr.findSlot(s.getTypeName(), s.getValue(), true) == null) 
                    return false;
            }
        }
        for (com.pullenti.ner.Slot s : mr.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (this.findSlot(s.getTypeName(), s.getValue(), true) == null) 
                    return false;
            }
        }
        return true;
    }
}
