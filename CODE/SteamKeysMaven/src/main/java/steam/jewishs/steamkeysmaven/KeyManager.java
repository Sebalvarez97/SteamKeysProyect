/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steam.jewishs.steamkeysmaven;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import Interface.Inventory;
import Model.*;
import TransporterUnits.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



public class KeyManager { 
 //INGRESA UNA NUEVA KEY
    private static void EnterKey(KeyDTO ktu) throws NonexistentEntityException{
         KeyState ks = EntityController.find(new KeyState(ktu.getState()));
         KeyType kt = EntityController.find(new KeyType(ktu.getType()));
            if(ks != null && kt != null){ 
                Key k = new Key();
                k.setBuyDate(ConvertDate(ktu.getbuydate())); //PERMITE GUARDAR EN BASE DE DATOS LA FECHA PARA STEAM
                k.setKeyState(ks);
                k.setKeyType(kt);
                EntityController.create(k);
        }else {
            throw new NonexistentEntityException("The type or the state does not exist");
        }
    }
    public static void EnterSomeKeys(String type, int cantidad, Date date) throws Exception{
        if(cantidad > 0){
            while(cantidad> 0){
                EnterKey(new KeyDTO(type,"Untradeable", date));
                SumarSaldo(-getKeyPrice());
                cantidad--;
            }
        }else throw new Exception("Quantity ingresed fail");
    }
    //DEVUELVE UN TYPESTATEDTO A PARTIR DE UN STRING DE ESTADO O TIPO
    private static TypeStateDTO getStateType(String str){
        TypeStateDTO ret = new TypeStateDTO();
        if(EntityController.findIfExist(new KeyState(str))){
            ret.setDescription(str);
        }else if(EntityController.findIfExist(new KeyType(str))){
            ret.setDescription(str);
        }
        return ret;
    }
    //DEVUELVE UN ESTADO A PARTIR DE UN STRING
    private static KeyState getState(String str){
        return EntityController.find(new KeyState(str));
    }
    //DEVUELVE UN TIPO A PARTIR DE UN STRING
    private static KeyType getType(String str){
        return EntityController.find(new KeyType(str));
    }
    //CREA UN ESTADO CON EL STRING INGRESADO
    public static void EnterState(String name) throws Exception{
        KeyState ks = new KeyState();
        ks.setStateDescription(name);
        if(!EntityController.findIfExist(ks)){
            EntityController.create(ks);
        }else{
            throw new Exception("The entity already exists");
        }  
    }
    //BORRA UN ESTADO DE LLAVE CON EL STRING INGRESADO
    public static void DeleteState(String name) throws NonexistentEntityException{
        KeyState ks = EntityController.find(new KeyState(name));
        EntityController.destroy(ks);
    }
    //CREA UN TIPO CON EL STRING INGRESADO
    public static void EnterType(String name) throws Exception{
        KeyType kt = new KeyType();
        kt.setTypeDescription(name);
         if(!EntityController.findIfExist(kt)){
            EntityController.create(kt);
        }else{
            throw new Exception("The entity already exists");
        }
    }
    //BORRA UN TIPO DE LLAVE SEGUN EL STRING INGRESADO
    public static void DeleteType(String name) throws NonexistentEntityException{
        KeyType kt = EntityController.find(new KeyType(name));
        EntityController.destroy(kt);
    }
    //DEVUELVE LA LLAVE SEGUN EL ID INGRESADO
    public static KeyDTO getKeyDTO(long id) throws NonexistentEntityException{
        Key key = EntityController.find(new Key(id));
        KeyDTO ret = new KeyDTO();
        ret.setBuydate(key.getBuyDate());
        ret.setId(key.getId());
        ret.setState(key.getKeyState().getStateDescription());
        ret.setType(key.getKeyType().getTypeDescription());
        if(key.getTrade() != null){
            ret.setTrade(key.getTrade().getId());
        }else ret.setTrade(null);
        return ret;
    }
    //DEVUELVE LA KEY A PARTIR DE UN KEYDTO PATRON
    private static Key getKey(KeyDTO patron) throws NonexistentEntityException{
        Key k = new Key();
        k.setId(patron.getId());
        Key key = EntityController.find(k);
        return key;
    }
    //BORRA LA KEY
    public static void DeleteKey(KeyDTO ktu) throws NonexistentEntityException{
        Key key = new Key();
        key.setId(ktu.getId());
        EntityController.destroy(key);
        UpdateHistory();
    }
    //DEVUELVE SI ES TRADEABLE LA LLAVE
    private static boolean isTradeable(Key key){
        return HavePassed(key.getBuyDate(), 8);      
    }
    //DEVUELVE TRUE SI HAN PASADO LA CANTIDAD DE DIAS INDICADOS DESDE LA FECHA INDICADA
    private static boolean HavePassed(Date date, int days){
        Date datenow = Calendar.getInstance().getTime();
        if(datenow.after(SumResDate(date, days))){
            return true;
        }else{
            return false;
        }
    }
    //FECHA DE LIBERACION
    public static Date ReleaseDate(Date date){
        return SumResDate(date, 8);
    }
    //SUMA O RESTA LOS DIAS INGRESADOS A LA FECHA INGRESADA
    private static Date SumResDate(Date date, int value){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }
    //TRANSFORMA UN CONJUNTO DE INT EN UN DATE
    public static Date ConvertDate(int day, int month, int year){;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, day);
        return cal.getTime();
    }
    //ACTUALIZA EL ESTADO DE LAS LLAVES DEL INVENTARIO 
    public static void UpdateState() throws NonexistentEntityException, Exception {
            List<KeyDTO> keys = KeyManager.ListWithStateKeys(new KeyDTO("","Untradeable"));
            for(KeyDTO key : keys){
                Key k = getKey(key);
                if(isTradeable(k)){
                    k = ChangeState(k, "Tradeable");
                    EntityController.Edit(k);
                }
            }
    }
    //DEVUELVE LA DIFERENCIA EN DIAS
    public static int DayDiference(Date date1, Date date2){
        long startime = date1.getTime();
        long endtime = date2.getTime();
        long diff = endtime - startime;
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        
    }
    //GENERAR UN STRING CON LA FECHA
    public static String SimpleFormatDate(Date date){
        String returned;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1;
        int year = cal.get(Calendar.YEAR);
        returned = day + "-" +month  + "-" + year;
        return returned;
    }
    //DEVUELVE LA CANTIDAD DE LLAVES CON CIERTO ESTADO
    public static int CantWithState(TypeStateDTO dto) throws NonexistentEntityException{
        List<KeyDTO> keys = ListKeys();
        int cantidad = 0;
        for(KeyDTO k : keys){            
            if(k.getState().equals(dto.getDescription())){
                cantidad++;
            }
        }
        return cantidad;
    }
    //DEVUELVE LA FECHA CONVERTIDA AL HORARIO DE STEAM 
    public static Date ConvertDate(Date date){
        ZonedDateTime inicial = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime finaldate = inicial.withZoneSameInstant(ZoneId.of("GMT+1")); //TRABAJAR CON LA HORA DE GMT
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(finaldate.getYear(), finaldate.getMonthValue()-1, finaldate.getDayOfMonth(), finaldate.getHour(), finaldate.getMinute());
        Date returnated = cal.getTime();
        return returnated;
    }
    //DEVUELVE EL VALOR DINERO TOTAL EN STEAM
    public static int getTotalMoney() throws NonexistentEntityException{
        int total = 0;
        int keycant = CantWithState(new TypeStateDTO("Tradeable")) + CantWithState(new TypeStateDTO("Untradeable"));
        int keyprice = findParameter("KeysPrice").getValue();
        int saldo = findParameter("Saldo").getValue();
        total = keycant * keyprice + saldo;
        return total;
    }
    //DEVUELVE EL VALOR EN DINERO DE LAS LLAVES TOTALES
    public static int getKeysMoney() throws NonexistentEntityException{
        int total = 0;
        int keycant = CantWithState(new TypeStateDTO("Tradeable")) + CantWithState(new TypeStateDTO("Untradeable"));
        int keyprice = findParameter("KeysPrice").getValue();
        total = keycant * keyprice;
        return total;
    }
    //DEVUELVE EL VALOR DEL SALDO
    public static int getBalanceMoney(){
        return findParameter("Saldo").getValue();
    }
    //DEVUELVE EL VALOR DE LAS LLAVES
    public static int getKeyPrice(){
        return findParameter("KeysPrice").getValue();
    }
    //DEVUELVE TRUE SI LA CADENA INGRESADA ES UN NUMERO, SINO DEVUELVE FALSE
    public static boolean isNumber(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
    //A PARTIR DE UN ENTERO DEVUELVE EL STRING CORRESPONDIENTE AL VALOR PARA MOSTRAR
    public static String numberConvertor(int i){
        double x = (double) i;
        x = x/100;
        return String.valueOf(x);
    }
    //A PARTIR DE UN STRING DEVUELVE EL ENTERO QUE LE CORRESPONDE CON 2 DECIMALES MAXIMO
    public static int numberConvertor(String str) throws Exception{
            int pointindex = str.indexOf(".");
                if(pointindex != -1){
                    if(isNumber(str)){
                        String subcadena = str.substring(pointindex, str.length());
                        if(subcadena.length() >= 3){
                            int finalindex = pointindex +3;
                            str = str.substring(0,finalindex);
                            str = str.replace(".", "");
                            return Integer.parseInt(str);
                        }else if(subcadena.length() < 3){
    //                      str = str + "0";
                            return numberConvertor(str + "0");
                        }
           
                    }else{
                        throw new Exception(str + " is not a number");
                    }
                }else{
                        return numberConvertor(str + ".00");
                }
         return 0;
    }
    //CAMBIA EL ESTADO DE LA KEY
    private static Key ChangeState(Key k, String state){
        KeyState ks = EntityController.find(new KeyState(state));
        k.setKeyState(ks);
        return k;
    }
    //CAMBIA EL ESTADO DE UNA LISTA DE KEYS
    private static List<Key> ChangeState(List<Key> keys, String state) throws Exception{
        List<Key> ret = new ArrayList();
        for(Key key :  keys){
            key = KeyManager.ChangeState(key,state);
            EntityController.Edit(key);
            key = EntityController.find(key);
            ret.add(key);
        }
        return ret;
    }
    //LISTA LOS STEAMITEMS A PARTIR DE UNA LISTA DE STEAMITEMSDTO 
    private static List<SteamItem> ListSteamItems(List<SteamItemDTO> list) throws Exception {
        List<SteamItem> ret = new ArrayList(); 
        for(SteamItemDTO row : list){
            ret.add(getItem(row));
        }
        return ret;
    }
    private static SteamItem getItem(SteamItemDTO dto) throws Exception{
        SteamItem item = new SteamItem();
        if(dto.getId() != null){
            item.setId(dto.getId());
            item = EntityController.find(item);
            //EDITO
            item.setPending(dto.isIspending());
            item.setSellprice(dto.getSellprice());
            item.setStoreprice(dto.getStoreprice());
            EntityController.Edit(item);
            item = EntityController.find(item);
        }else{
            item.setPending(dto.isIspending());
            item.setSellprice(dto.getSellprice());
            item.setStoreprice(dto.getStoreprice());
            EntityController.create(item);
            item = EntityController.getLast(item);
        }
        return item;
    }
    //LISTA STEAMITEMDTO A PARTIR DE UNA LISTA DE STEAMITEMS
    private static List<SteamItemDTO> ListSteamItemsDTO(List<SteamItem> items){
        List<SteamItemDTO> ret = new ArrayList();
        for(SteamItem item : items){
            ret.add(getItemDTO(item));
        }
        return ret;
    }
    //DEVUELVE UN ITEMDTO A PARTIR DE UN ITEM
    private static SteamItemDTO getItemDTO(SteamItem item){
        SteamItemDTO dto = new SteamItemDTO();
        dto.setId(item.getId());
        dto.setIspending(item.isPending());
        dto.setSellprice(item.getSellprice());
        dto.setStoreprice(item.getStoreprice());
        dto.setTrade_id(item.getTrade().getId());
        return dto;
    }
    //LISTA LAS LLAVES A PARTIR DE UNA LISTA DE KEYDTO
    private static List<Key> ListSteamKeys(List<KeyDTO> list) throws NonexistentEntityException{
        List<Key> llaves = new ArrayList();
        for(KeyDTO key : list){
          Key k = KeyManager.getKey(key);
          llaves.add(k);
        }
        return llaves;
    }
    //DEVUELVE EL VALOR DE GANANCIA DE UN TRADE
    private static int getGanancia(List<SteamItem> list) throws Exception{
        int ganancia = 0;
        for(SteamItem item : list){
            if(!item.isPending()){
                ganancia = ganancia + profit(item.getSellprice());
            }
        }
        return ganancia;
    }
    //DEVUELVE TRUE SI ES POSIBLE COMPRAR LLAVES
    public static boolean isPossibleToBuyKeys(){
        if(getCantUcanBuy(getBalanceMoney()) == 0){
            return false;
        }else return true;
    }
    //DEVUELVE LA CANTIDAD DE LLAVES QUE SE PUEDE COMPRAR
    public static int getCantUcanBuy(int money){
        int ret = 0;
        int keyprice = KeyManager.getKeyPrice();
        if(money>= keyprice){
            ret = Math.round(money/keyprice);
        }
        return ret;
    }
    //CREA UN NUEVO TRADE EN BASE DE DATOS
    public static void EnterTrade(TradeDTO tradedto) throws Exception {    
        //SE CREA EL TRADE Y SE SETEAN LOS PARAMETROS
        if(tradedto.getId() == null){
            CreateTradeByDTO(tradedto);
        }else{
            EditTrade(tradedto);
        }
    }
    //CREA A PARTIR DE UN TRADEDTO
    private static void CreateTradeByDTO(TradeDTO dto) throws NonexistentEntityException, Exception{
        List<Key> llaves = ChangeState(ListSteamKeys(dto.getKeys()),"Traded");
        List<SteamItem> items = ListSteamItems(dto.getItems());
        int ganancia = getGanancia(items);
        Trade trade = new Trade();
        trade.setBalancestore(dto.getBalance());
        trade.setCantkey(dto.getKeys().size());
        trade.setDateoftrade(Calendar.getInstance().getTime());
        trade.setGanancia(ganancia);
        trade.setKeyprice(getKeyPrice());
        trade.setPriceinstore(dto.getPriceinstore());
        //AGREGAR LAS LLAVES E ITEMS NUEVOS
        trade.setKeytraded(llaves);
        trade.setItems(items);
        EntityController.create(trade);
        SumarSaldo(trade.getGanancia()); 
    }
    
    private static void EditTrade(TradeDTO tradedto) throws Exception{
        Trade trade = EntityController.find(new Trade(tradedto.getId()));
        List<SteamItem> items = ListSteamItems(tradedto.getItems());
        List<Key> llaves = ChangeState(ListSteamKeys(tradedto.getKeys()),"Traded");
        int newganancia = getGanancia(items);
        int dif = newganancia - trade.getGanancia();
        trade.setBalancestore(tradedto.getBalance());
        trade.setCantkey(tradedto.getCantkeys());
        trade.setGanancia(newganancia);
         //AGREGAR LAS LLAVES E ITEMS NUEVOS
        trade.setKeytraded(llaves);
        trade.setItems(items);
        EntityController.Edit(trade);
        SumarSaldo(dif);
    }

    //RETORNA TRUE SI EL TRADE ES EDITABLE
    public static boolean isEditable(long id){
        Trade trade = EntityController.find(new Trade(id));
        if(trade.getBalancestore() != 0 || AnyPending(trade)){
            return true;
        }else return false; 
    }
    //RETORNA TRUE SI ALGUN ITEM QUEDO PENDIENTE
    private static boolean AnyPending(Trade trade){
        for(SteamItem item : trade.getItems()){
            if(item.isPending()){
                return true;
            }
        }
        return false;
    }
    //DEVUELVE UN TRADEDTO A PARTIR DE UN ID
    public static TradeDTO getTrade(long id) throws NonexistentEntityException{
        Trade trade = EntityController.find(new Trade(id));
        return getTradeDTO(trade);
    }
    //DEVUELVE UN TRADEDTO A PARTIR DE UN TRADE
    private static TradeDTO getTradeDTO(Trade trade) throws NonexistentEntityException{
        TradeDTO dto = new TradeDTO();
        dto.setBalance(trade.getBalancestore());
        dto.setCantkeys(trade.getCantkey());
        dto.setDateoftrade(trade.getDateoftrade());
        dto.setGanancia(trade.getGanancia());
        dto.setId(trade.getId());
        dto.setIngameprice(trade.getKeyprice());
        dto.setItems(KeyManager.ListSteamItemsDTO(trade.getItems()));
        dto.setKeys(KeyManager.ListKeysByList(trade.getKeytraded()));
        dto.setPriceinstore(trade.getPriceinstore());
        return dto;
    }
    //VALIDA LA SELECCION PARA TRADEAR
    public static boolean ValidateTradeSelection(List<KeyDTO> list){
        boolean validation = true;
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(!dto.getState().equals("Tradeable")){
                validation = false;
            }
        }
        return validation;
    } 
    //ORDENA ALFABETICAMENTE UNA LISTA DE TYPESTATESDTO
    public static List<TypeStateDTO> AlphabeticOrder(List<TypeStateDTO> lista){
       List<String> list = ListStatesTypesString(lista);
       Collections.sort(list);
       return ListStateTypesDTO(list);
    }
    //DEVUELVE UNA LISTA DE TYPESTATESDTO A PARTIR DE UNA LISTA DE STRINGS
    public static List<TypeStateDTO> ListStateTypesDTO(List<String> list){
        List<TypeStateDTO> ret = new ArrayList();
        for(String it : list){
            TypeStateDTO dto = KeyManager.getStateType(it);
                if(dto != null){
                    ret.add(dto);
                }
            }
        return ret;
    }
    //DEVUELVE UNA LISTA DE STRING A PARTIR DE UNA LISTA DE TYPESTATEDTO
    public static List<String> ListStatesTypesString(List<TypeStateDTO> list){
        List<String> ret = new ArrayList();
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            TypeStateDTO dto = (TypeStateDTO) iter.next();
            String st = dto.getDescription();
            ret.add(st);
        }
        return ret;
    }
    //DEVUELVE EL ID DE LA KEY CON FORMATO ID-ALGO
    public static long getKeyID(String str){
        int guion = str.indexOf("-");
        String id = str.substring( 0,guion);
        return Long.parseLong(id);
    }
    //PERMITE VENDER UNA KEY
    public static void SellKey(KeyDTO key, int sellprice) throws NonexistentEntityException, Exception{
        int ganancia = KeyManager.profit(sellprice);
        Key k = KeyManager.getKey(key);
        k = KeyManager.ChangeState(k, "Sold");
        EntityController.Edit(k);
        SumarSaldo(ganancia);
    }
    //SUMA EL VALOR AL SALDO
    public static void SumarSaldo(int ganancia) throws Exception{
        int saldo = findParameter("Saldo").getValue();
        saldo = saldo+ ganancia;
        setValue(new ParameterDTO("Saldo","",saldo));
    }
    //DEVUELVE TRUE SI LA LLAVE SE ENCUENTRA EN LA LISTA
    public static boolean keyInTheList(KeyDTO dto, List<KeyDTO> list){
        boolean ret = false;
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            KeyDTO listelement = (KeyDTO) iter.next();
            if(listelement.getId() == dto.getId()){
                ret = true;
            }
        }
        return ret;
    }
    private static boolean itemInTheList(SteamItem item, List<SteamItem> items){
        boolean ret = false;
        for(SteamItem i : items){
            if(i.getId() == item.getId()){
                ret = true;
            }
        }
        return ret;
    }
    private static List<SteamItem> NonRepited(List<SteamItem> list1, List<SteamItem> list2){
        List<SteamItem> ret = new ArrayList();
        for(SteamItem item : list2){
            if(!itemInTheList(item, list1)){
                ret.add(item);
            }
        }
        return ret;
    }
    //DEVUELVE LAS LLAVES RESTANTES QUE TIENEN EL MISMO ESTADO QUE LA INGRESADA
    public static List<KeyDTO> getmissingKeys(List<KeyDTO> list) throws NonexistentEntityException{
        List<KeyDTO> ret = new ArrayList();
        if(!list.isEmpty()){
            KeyDTO firstinlist = list.get(0);
            Iterator iter = ListWithStateKeys(new KeyDTO("",firstinlist.getState())).iterator();
            while(iter.hasNext()){
                KeyDTO listelement = (KeyDTO) iter.next();
                if(!keyInTheList(listelement, list)){
                    ret.add(listelement);
                }   
            }
        }
        return ret;
    }
    //DEVUELVE UNA LISTA CON SOLO LAS LLAVES DEL ESTADO INDICADO
    public static List<KeyDTO> ListWithStateKeys(KeyDTO patron) throws NonexistentEntityException{
        List<KeyDTO> list = new ArrayList();
        Iterator iter = ListKeys().iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(dto.getState().equals(patron.getState())){
                list.add(dto);
            }
        }
        return list;
    }
    //LISTA LOS VALORES DE VMR
    public static List<Object[]> ListVMR(double storeprice) throws Exception{
        double keysellprice = (double) sellPrice(findParameter("KeysPrice").getValue());
        int razon = (int) (keysellprice/storeprice * 100);
        List<Object[]> VMRlist = new ArrayList();
        for(int i = 5; i<=1001; i++){
            double x = (double) i;
            x = x/100;
            int calculated = i *razon;
            double c = (double) calculated;
            c = c/10000;
            calculated = numberConvertor(String.valueOf(c));
            c = (double) calculated;
            c = c/100;
            Object[] row = {x,c};
            VMRlist.add(row);
        }
        return VMRlist;
    }
    //LISTA LAS KEYS POR FECHA
    public static List<KeyDTO> ListKeysOrderByDate() throws Exception{
        List<KeyDTO> list = ListKeys();
            list.sort(null);
            return list;
    }
    //DEVUELVE UNA LISTA DE LAS LLAVES PARA LA INTERFAZ
    public static List<KeyDTO> ListKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        List<KeyDTO> dtos = new ArrayList();
        for(Key key : keys){
            KeyDTO dto = new KeyDTO();
            dto.setId(key.getId());
            dto.setBuydate(key.getBuyDate());
            dto.setState(key.getKeyState().getStateDescription());
            dto.setType(key.getKeyType().getTypeDescription());
            if(key.getTrade() != null){
                dto.setTrade(key.getTrade().getId());
            }else dto.setTrade(null);
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA DE DTOS A PARTIR DE UNA LISTA DE KEYS
    private static List<KeyDTO> ListKeysByList(List<Key> keys) throws NonexistentEntityException{
        List<KeyDTO> ret = new ArrayList();
        for(Key key : keys){
            ret.add(getKeyDTO(key.getId()));
        }
        return ret;
    }
    //DEVUELVE UNA ROW DE TOTAL
    public static Integer[] getTotalOfItems(List<SteamItemDTO> lista) throws Exception{
        if(lista.isEmpty()){
            lista.add(new SteamItemDTO());
        }
        List<Integer[]> converted = new ArrayList();
        for(SteamItemDTO item : lista){
            Integer[] row = {item.getStoreprice(), item.getSellprice()};
            converted.add(row);
        }
        return getTotalizer(converted);
    }
    //DEVUELVE UN ARREGLO CON LOS TOTALES DE LAS COLUMNAS
    public static Integer[] getTotalizer(List<Integer[]> lista) throws Exception{
        Integer[] ret = new Integer[lista.get(0).length];
        for(int i = 0; i < ret.length;  i++){
            ret[i] = 0;
        }
        for(Integer[] row : lista){
            for(int i = 0; i< ret.length; i++){
                ret[i] = ret[i] + row[i];
            }
        }
        return ret;
    }
    //DEVUELVE UNA LISTA PARA MOSTRAR LOS TRADE
    public static List<Object[]> getTradeList() throws Exception{
        List<Object[]> ret = new ArrayList();
        List<Trade> trades = EntityController.ListTrades();
        Collections.reverse(trades);
        for(Trade trade : trades){
            int keyprice = trade.getKeyprice();
            int profit = trade.getGanancia();
            int cant = trade.getCantkey();
            if(cant == 0){
                cant = 1;
                keyprice = 0;
            }
            int total = profit-(keyprice*cant);
                double cantkey = (double) cant;
                double t = (double) total;
                double profitxkey = t/cantkey;
                profitxkey = profitxkey/100;
                String pk = String.valueOf(profitxkey);
                Object[] listelement = {trade.getId(),SimpleFormatDate(trade.getDateoftrade()), trade.getCantkey(),numberConvertor(numberConvertor(pk)) , numberConvertor(trade.getBalancestore()), numberConvertor(trade.getPriceinstore())};
                ret.add(listelement);
        }
        return ret;
    }
    //CREA UN PARAMETRO NUEVO
    private static void EnterParameter(ParameterDTO dto){
        SteamParameters p = new SteamParameters();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setValue(dto.getValue());
        EntityController.create(p);
    }
    //SETEA EL VALOR DEL PARAMETRO INGRESADO(ES UN PATRON NO HACE FALTA AGREGAR LA DESCRIPCION)
    private static void setValue(ParameterDTO dto) throws Exception{
        SteamParameters sp = EntityController.find(new SteamParameters(dto.getName()));
        sp.setValue(dto.getValue());
        EntityController.Edit(sp);
    }
    //CAMBIA EL VALOR DEL SALDO
    public static void UpdateSaldo(int saldo) throws Exception{
        setValue(new ParameterDTO("Saldo", "", saldo));
        UpdateHistory();
    }
    //CAMBIA EL PRECIO DE LAS KEY 
    public static void UpdateKeyPrice(int price) throws Exception{
        setValue(new ParameterDTO("KeysPrice","", price));
        UpdateHistory();
    }
    //GUARDA UN ESTADO DE HISTORIAL CON LAS 3 VARIABLES IMPORTAANTES
    private static void SaveHistory() throws NonexistentEntityException{
            History h = new History();
            h.setDate(Calendar.getInstance().getTime());
            h.setTotalmoney(KeyManager.getTotalMoney());
            h.setType("Total");
            EntityController.create(h);
        
            h = new History();
            h.setDate(Calendar.getInstance().getTime());
            h.setTotalmoney(KeyManager.getKeyPrice());
            h.setType("KeyValue");
            EntityController.create(h);
        
            h = new History();
            h.setDate(Calendar.getInstance().getTime());
            h.setTotalmoney(KeyManager.getBalanceMoney());
            h.setType("Balance");
            EntityController.create(h);
    }
    //ACTUALIZA EL HISTORIAL SI FUERA NECESARIO
    private static void UpdateHistory() throws NonexistentEntityException{
        DeleteUnUsedHistory(30);  
        List<History> last = EntityController.getLast(new History());
        if(last.isEmpty()){
            SaveHistory();
        }
        else if(getLastHistoryOf("Total").getTotalmoney() != KeyManager.getTotalMoney()){
            SaveHistory();
        }
    }
    //BORRA LOS HISTORY REDUNDANTES
    private static void DeleteUnUsedHistory(int days) throws NonexistentEntityException{
        List<History> hist = ListHistoryByType("Total");
        if(!hist.isEmpty()){
            for(History h : hist){
                if(HavePassed(h.getDate(),days)){
                    EntityController.destroy(h);
                }
            }
        }
        hist = ListHistoryByType("Balance");
        if(!hist.isEmpty()){
            for(History h : hist){
                if(HavePassed(h.getDate(),days)){
                    EntityController.destroy(h);
                }
            }
        }
    }
    //DEVUELVE EL ULTIMO HISTORY DEL TIPO INGRESADO
    private static History getLastHistoryOf(String str){
        List<History> lista = ListHistoryByType(str);
        return lista.get(lista.size()-1);
    }
    //LISTA EL HISTORY SEGUN EL TIPO
    private static List<History> ListHistoryByType(String type){
        List<History> ret = new ArrayList();
        List<History> hist = EntityController.ListHistory();
        for(History h: hist){
            if(h.getType().equals(type)){
                ret.add(h);
            }
        }
        return ret;
    }
    //MODIFICAR VALOR DE LA KEY
    private static void setKeyPrice(int price) throws Exception{
        SteamParameters sp = EntityController.find(new SteamParameters("KeysPrice"));
        sp.setValue(price);
        try {
            EntityController.Edit(sp);
        } catch (Exception ex) {
            throw new Exception("THE PARAMETER DOES NOT EXIST");
        }
    }
    //PERMITE OBTENER UN VALOR NUMERO REPRESENTATIVO DEL DIA Y HORA (EJEMPLO DIA 28, HORA 17, SERIA 287500)
    private static int getDayTimeValue(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        double razon = 1.0/24.00;
        double razonmin = 1.0/60.00;
        double day = fecha.getDayOfMonth()*10000;
        double hour = fecha.getHour()* razon * 10000;
        double min = fecha.getMinute()*razonmin*100;
        double total = day + hour + min;
        return (int) total;
    }
    //DEVUELVE EL DIA DEL MES CON LA FECHA INGRESADA
    public static int getDay(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        return fecha.getDayOfMonth();
    }
    //DEVUELVE LA HORA
    public static int getHour(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        return fecha.getMonthValue();
    }
    //DEVUELVE LOS MINUTOS
    public static int getMinutes(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        return fecha.getMonthValue();
    }
    //DEVUELVE EL MES DE LA FECHA INGRESADA
    public static int getMonth(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        return fecha.getMonthValue();
    }
    //DEVUELVE EL AÑO DE LA FECHA INGRESADA
    public static int getYear(Date date){
        ZonedDateTime fecha = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        return fecha.getYear();
    }
    //DEVUELVE EL VALOR DE LA CONTRASEÑA GLOBAL
    public static String getGlobalPassword(){
        return KeyManager.findParameter("Password").getDescription();
    }
    private static void ChangeGlobalPassword(String nueva) throws Exception{
        SteamParameters sp = EntityController.find(new SteamParameters("Password"));
        sp.setDescription(nueva);
        EntityController.Edit(sp);
    }
    //BUSCA EL PARAMETRO INDICADO
    private static ParameterDTO findParameter(String name){
        SteamParameters sp = EntityController.find(new SteamParameters(name));
        ParameterDTO p = new ParameterDTO();
        p.setDescription(sp.getDescription());
        p.setName(sp.getName());
        p.setValue(sp.getValue());
        return p;
    }
    //LISTA LOS HISTORES PARA EL USO EN LA INTERFAZ //NO TIENE UTILIDAD ACTUAL
    public static List<Integer[]> ListHistory(){
        List<Integer[]> ret = new ArrayList();
        List<History> list = EntityController.ListHistory();
        if(!list.isEmpty()){
            list.forEach((h) -> {
                Integer[] ob = {getDayTimeValue(h.getDate()),h.getTotalmoney()};
                ret.add(ob);
            });
        }
        return ret;
    }
    //LISTA LOS HISTORIES PARA USO EN LA INTERFAZ PERO POR TIPO
    public static List<Object[]> ListHByType(String type){
        List<Object[]> ret = new ArrayList();
        List<History> list = KeyManager.ListHistoryByType(type);
        if(!list.isEmpty()){
            list.forEach((h) -> {
                Date fecha = h.getDate();
                Object[] ob = {fecha,h.getTotalmoney()};
                ret.add(ob);
            });
        }
        return ret;
    }
    //DEVUELVE UNA LISTA DE LOS TIPOS DE LLAVE EXISTENTES
    public static List<TypeStateDTO> ListTypes() throws NonexistentEntityException{
        List<KeyType> types = EntityController.ListTypes();
        KeyType type;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = types.iterator();
        while(iter.hasNext()){
            type = (KeyType) iter.next();
            TypeStateDTO dto = new TypeStateDTO(type.getId(), type.getTypeDescription());
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA CON LOS ESTADOS
        public static List<TypeStateDTO> ListStates() throws NonexistentEntityException{
        List<KeyState> states = EntityController.ListStates();
        KeyState state;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = states.iterator();
        while(iter.hasNext()){
            state = (KeyState) iter.next();
            TypeStateDTO dto = new TypeStateDTO(state.getId(), state.getStateDescription());
            dtos.add(dto);
        }
        return dtos;
    }
        
    //DESTRUYE TODAS LAS LLAVES
    public static void DeleteAllKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        for(Key key : keys){
            EntityController.destroy(key);
        }
    }
    //DEVUELVE EL VALOR DE BENEFICIO A PARTIR DE UN VALOR DE VENTA
    public static int profit(int valor) throws Exception {     //buscar el valor de ganancia de un item a partir del valor de venta
        int buscar = valor;          //pasa el valor a int con dos decimales
        int cantidad = 1000000;                     //cantidad de digitos del array (de 0.01 a (cantidad/100)-1)
        int[] matriz = new int[cantidad];           //creacion del array
        for (int i = 1; i < cantidad; i++) {        //insercion de valores al array
            if (i < 20) {                           //si el valor esta entre 0.01 y 0.20 se le suman solo 2 centavos
                matriz[i] = i + 2;                  //es porque se tosquea si lo hago asi
            } else {
                matriz[i] = i + (i / 10) + (i / 20);//valor mas el 10% mas el 5% truncado
            }
        }
        int i = 1;                                  //auxiliar para buscar
        while (true) {
            if (i == cantidad) {                    //si llega a la cantidad, es porque no encontro el valor
                //return -1; 
                throw new Exception("bad enter, value does not exist");//o el valor es muy alto, devuelve -1
            }
            if (matriz[i] == buscar) {              //si encuentra el valor
                return i;            //se retorna el numero del indice del valor
            } else {                                //si no encuentra el valor
                i++;                                //suma uno y busca el siguiente
            }
        }
    }
    //DEVUELVE EL VALOR DE VENTA A PARTIR DE UN MONTO DE BENEFICIO
    public static int sellPrice(int valor){
        int i = valor;
        if(i<=2000){
            return  i/100 + 2;
        }else{
            return (i + (i/10) + (i/20));
        } 
    }
    private static void InitAll(){
        InitParameters();
        InitStates();
        InitTypes();
    }
    private static void InitParameters(){
        ParameterDTO dto = new ParameterDTO();
        dto.setName("KeysPrice");
        dto.setDescription("price of the key ingame");
        dto.setValue(9599);
        KeyManager.EnterParameter(dto);
        dto = new ParameterDTO();
        dto.setName("Saldo");
        dto.setDescription("balance in steam accoun");
        dto.setValue(0);
        KeyManager.EnterParameter(dto);
        dto = new ParameterDTO();
        dto.setName("Password");
        dto.setDescription("1234");
        dto.setValue(0);
        KeyManager.EnterParameter(dto);
    }
    private static void InitStates(){
        try{
            KeyManager.EnterState("Untradeable");
            KeyManager.EnterState("Tradeable");
            KeyManager.EnterState("Traded");
            KeyManager.EnterState("Sold");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void InitTypes(){
        try{
            KeyManager.EnterType("Croma 2");
            KeyManager.EnterType("Croma");
            KeyManager.EnterType("Croma 3");
            KeyManager.EnterType("Esports");
            KeyManager.EnterType("Guantes");
            KeyManager.EnterType("Espectro 2");
            KeyManager.EnterType("Espectro");
            KeyManager.EnterType("Clutch");
            KeyManager.EnterType("Gamma");
            KeyManager.EnterType("Gamma 2");
            KeyManager.EnterType("Wildfire");
            KeyManager.EnterType("Phoenix");
            KeyManager.EnterType("Sombria");
            KeyManager.EnterType("CS:GO");
            KeyManager.EnterType("Cazador");
            KeyManager.EnterType("Revolver");
            KeyManager.EnterType("Breakout");
            KeyManager.EnterType("Alfanje");
            KeyManager.EnterType("Vanguard");
            KeyManager.EnterType("Invernal Offensive");
            KeyManager.EnterType("Hydra");
            KeyManager.EnterType("cápsula comunidad 1");
            KeyManager.EnterType("cápsula CS:GO");
            KeyManager.EnterType("Horizon");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    } 
    public static int KeyCounter(){
     return EntityController.KeyCant();
    }
    public static int StateCounter(){
     return EntityController.StateCant();
    }
    public static int TypeCounter(){
     return EntityController.TypeCant();
    }
  
  
  //INTERFAZ
  public static void InitInventory(){
      Inventory inventory = new Inventory();
      inventory.setTitle("SteamKeysApp");
      inventory.setVisible(true);
  }

  
  public static void main(String[] args){

      try{
          InitInventory();
          
      }catch(java.lang.IndexOutOfBoundsException ex){
          InitAll();
          InitInventory();
      }
      
      System.out.println("FIN");
      
  }
    
}
