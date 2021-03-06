/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core.internal;

/**
 * Поддержка работы с таблицами, расположенными в текстах. 
 *  Начало таблицы - символ 1Eh, конец - 1Fh, ячейки оканчиваются 07h, 
 *  комбинация 0D 0A 07 - конец строки. 
 *  Данную структуру формирует функция извлечения текстов (ExtractText), так что это - для 
 *  обратного восстановления таблицы в случае необходимости.
 */
public class TableHelper {

    /**
     * Получить список строк таблицы
     * @param t начальная позиция
     * @param maxChar максимальная позиция (0 - не ограничена)
     * @param mustBeStartOfTable при true первый символ должен быть 1Eh
     * @return список строк
     */
    public static java.util.ArrayList<TableRowToken> tryParseRows(com.pullenti.ner.Token t, int maxChar, boolean mustBeStartOfTable) {
        if (t == null) 
            return null;
        boolean isTab = false;
        if (mustBeStartOfTable) {
            if (!t.isChar((char)0x1E)) 
                return null;
            isTab = true;
        }
        com.pullenti.unisharp.Outargwrapper<Boolean> wrapisTab532 = new com.pullenti.unisharp.Outargwrapper<Boolean>(isTab);
        TableRowToken rw = parse(t, maxChar, null, wrapisTab532);
        isTab = (wrapisTab532.value != null ? wrapisTab532.value : false);
        if (rw == null) 
            return null;
        java.util.ArrayList<TableRowToken> res = new java.util.ArrayList<TableRowToken>();
        res.add(rw);
        for (t = rw.getEndToken().getNext(); t != null; t = t.getNext()) {
            com.pullenti.unisharp.Outargwrapper<Boolean> wrapisTab531 = new com.pullenti.unisharp.Outargwrapper<Boolean>(isTab);
            TableRowToken rw0 = parse(t, maxChar, rw, wrapisTab531);
            isTab = (wrapisTab531.value != null ? wrapisTab531.value : false);
            if (rw0 == null) 
                break;
            res.add((rw = rw0));
            t = rw0.getEndToken();
            if (rw0.lastRow) 
                break;
        }
        TableRowToken rla = res.get(res.size() - 1);
        if (((rla.lastRow && rla.cells.size() == 2 && rla.cells.get(0).colSpan == 1) && rla.cells.get(0).rowSpan == 1 && rla.cells.get(1).colSpan == 1) && rla.cells.get(1).rowSpan == 1) {
            java.util.ArrayList<TableCellToken> lines0 = rla.cells.get(0).getLines();
            java.util.ArrayList<TableCellToken> lines1 = rla.cells.get(1).getLines();
            if (lines0.size() > 2 && lines1.size() == lines0.size()) {
                for (int ii = 0; ii < lines0.size(); ii++) {
                    rw = new TableRowToken((ii == 0 ? lines0.get(ii).getBeginToken() : lines1.get(ii).getBeginToken()), (ii == 0 ? lines0.get(ii).getEndToken() : lines1.get(ii).getEndToken()));
                    rw.cells.add(lines0.get(ii));
                    rw.cells.add(lines1.get(ii));
                    rw.eor = rla.eor;
                    if (ii == (lines0.size() - 1)) {
                        rw.lastRow = rla.lastRow;
                        rw.setEndToken(rla.getEndToken());
                    }
                    res.add(rw);
                }
                res.remove(rla);
            }
        }
        for (TableRowToken re : res) {
            if (re.cells.size() > 1) 
                return res;
        }
        return null;
    }

    private static TableRowToken parse(com.pullenti.ner.Token t, int maxChar, TableRowToken prev, com.pullenti.unisharp.Outargwrapper<Boolean> isTab) {
        if (t == null || ((t.endChar > maxChar && maxChar > 0))) 
            return null;
        String txt = t.kit.getSofa().getText();
        com.pullenti.ner.Token t0 = t;
        if (t.isChar((char)0x1E) && t.getNext() != null) {
            isTab.value = true;
            t = t.getNext();
        }
        com.pullenti.ner.Token tt;
        TableInfo cellInfo = null;
        for (tt = t; tt != null && ((tt.endChar <= maxChar || maxChar == 0)); tt = tt.getNext()) {
            if (tt.isTableControlChar()) {
                cellInfo = new TableInfo(tt);
                if (cellInfo.typ != TableTypes.CELLEND) 
                    cellInfo = null;
                break;
            }
            else if (tt.isNewlineAfter()) {
                if (!isTab.value && prev == null) 
                    break;
                if ((tt.endChar - t.beginChar) > 100) {
                    if ((tt.endChar - t.beginChar) > 10000) 
                        break;
                    if (!isTab.value) 
                        break;
                }
                if (tt.getWhitespacesAfterCount() > 15) {
                    if (!isTab.value) 
                        break;
                }
            }
        }
        if (cellInfo == null) 
            return null;
        TableRowToken res = new TableRowToken(t0, tt);
        res.cells.add(TableCellToken._new533(t, tt, cellInfo.rowSpan, cellInfo.colSpan));
        for (tt = tt.getNext(); tt != null && ((tt.endChar <= maxChar || maxChar == 0)); tt = tt.getNext()) {
            t0 = tt;
            cellInfo = null;
            for (; tt != null && ((tt.endChar <= maxChar || maxChar == 0)); tt = tt.getNext()) {
                if (tt.isTableControlChar()) {
                    cellInfo = new TableInfo(tt);
                    break;
                }
                else if (tt.isNewlineAfter()) {
                    if (!isTab.value && prev == null) 
                        break;
                    if ((tt.endChar - t0.beginChar) > 400) {
                        if ((tt.endChar - t0.beginChar) > 20000) 
                            break;
                        if (!isTab.value) 
                            break;
                    }
                    if (tt.getWhitespacesAfterCount() > 15) {
                        if (!isTab.value) 
                            break;
                    }
                }
            }
            if (cellInfo == null) 
                break;
            if (cellInfo.typ == TableTypes.ROWEND && tt == t0) {
                res.setEndToken(tt);
                res.eor = true;
                break;
            }
            if (cellInfo.typ != TableTypes.CELLEND) 
                break;
            res.cells.add(TableCellToken._new533(t0, tt, cellInfo.rowSpan, cellInfo.colSpan));
            res.setEndToken(tt);
        }
        if ((res.cells.size() < 2) && !res.eor) 
            return null;
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar((char)0x1F)) {
            res.lastRow = true;
            res.setEndToken(res.getEndToken().getNext());
        }
        return res;
    }

    public static class TableInfo {
    
        public int colSpan = 0;
    
        public int rowSpan = 0;
    
        public com.pullenti.ner.core.internal.TableHelper.TableTypes typ = com.pullenti.ner.core.internal.TableHelper.TableTypes.UNDEFINED;
    
        public com.pullenti.ner.Token src;
    
        @Override
        public String toString() {
            return (typ.toString() + " (" + colSpan + "-" + rowSpan + ")");
        }
    
        public TableInfo(com.pullenti.ner.Token t) {
            src = t;
            if (t == null) 
                return;
            if (t.isChar((char)0x1E)) {
                typ = com.pullenti.ner.core.internal.TableHelper.TableTypes.TABLESTART;
                return;
            }
            if (t.isChar((char)0x1F)) {
                typ = com.pullenti.ner.core.internal.TableHelper.TableTypes.TABLEEND;
                return;
            }
            if (!t.isChar((char)7)) 
                return;
            String txt = t.kit.getSofa().getText();
            typ = com.pullenti.ner.core.internal.TableHelper.TableTypes.CELLEND;
            int p = t.beginChar - 1;
            if (p < 0) 
                return;
            if (((int)txt.charAt(p)) == 0xD || ((int)txt.charAt(p)) == 0xA) {
                typ = com.pullenti.ner.core.internal.TableHelper.TableTypes.ROWEND;
                return;
            }
            colSpan = (rowSpan = 1);
            for (; p >= 0; p--) {
                if (!com.pullenti.unisharp.Utils.isWhitespace(txt.charAt(p))) 
                    break;
                else if (txt.charAt(p) == '\t') 
                    colSpan++;
                else if (txt.charAt(p) == '\f') 
                    rowSpan++;
            }
        }
        public TableInfo() {
        }
    }


    public static boolean isCellEnd(com.pullenti.ner.Token t) {
        if (t != null && t.isChar((char)7)) 
            return true;
        return false;
    }

    public static boolean isRowEnd(com.pullenti.ner.Token t) {
        if (t == null || !t.isChar((char)7)) 
            return false;
        TableInfo ti = new TableInfo(t);
        return ti.typ == TableTypes.ROWEND;
    }

    public static class TableTypes implements Comparable<TableTypes> {
    
        public static final TableTypes UNDEFINED; // 0
    
        public static final TableTypes TABLESTART; // 1
    
        public static final TableTypes TABLEEND; // 2
    
        public static final TableTypes ROWEND; // 3
    
        public static final TableTypes CELLEND; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private TableTypes(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(TableTypes v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, TableTypes> mapIntToEnum; 
        private static java.util.HashMap<String, TableTypes> mapStringToEnum; 
        private static TableTypes[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static TableTypes of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            TableTypes item = new TableTypes(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static TableTypes of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static TableTypes[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, TableTypes>();
            mapStringToEnum = new java.util.HashMap<String, TableTypes>();
            UNDEFINED = new TableTypes(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            TABLESTART = new TableTypes(1, "TABLESTART");
            mapIntToEnum.put(TABLESTART.value(), TABLESTART);
            mapStringToEnum.put(TABLESTART.m_str.toUpperCase(), TABLESTART);
            TABLEEND = new TableTypes(2, "TABLEEND");
            mapIntToEnum.put(TABLEEND.value(), TABLEEND);
            mapStringToEnum.put(TABLEEND.m_str.toUpperCase(), TABLEEND);
            ROWEND = new TableTypes(3, "ROWEND");
            mapIntToEnum.put(ROWEND.value(), ROWEND);
            mapStringToEnum.put(ROWEND.m_str.toUpperCase(), ROWEND);
            CELLEND = new TableTypes(4, "CELLEND");
            mapIntToEnum.put(CELLEND.value(), CELLEND);
            mapStringToEnum.put(CELLEND.m_str.toUpperCase(), CELLEND);
            java.util.Collection<TableTypes> col = mapIntToEnum.values();
            m_Values = new TableTypes[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public TableHelper() {
    }
    public static TableHelper _globalInstance;
    
    static {
        _globalInstance = new TableHelper(); 
    }
}
