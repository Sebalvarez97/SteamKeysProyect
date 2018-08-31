/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steam.jewishs.steamkeysmaven;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import Interface.AddKey;
import Interface.Configurations;
import Interface.Inventory;
import Model.*;
import TransporterUnits.*;
import java.awt.Window;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



public class KeyManager { 
    
 //INGRESA UNA NUEVA KEY
    public static void EnterKey(KeyDTO ktu){
        try{
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
        }catch(NonexistentEntityException Ne){
            //se enviaran al ExceptionManager
        }
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
    //CREA UN ESTADO CON EL STRING INGRESADO
    private static void EnterState(String name){
        KeyState ks = new KeyState();
        ks.setStateDescription(name);
        EntityController.create(ks);
    }
    //DEVUELVE LA LLAVE SEGUN EL ID INGRESADO EN EL KEYDTO PATRON
    public static KeyDTO getKeyDTO(KeyDTO patron) throws NonexistentEntityException{
        KeyDTO ret = null;
        Iterator iter = ListKeys().iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(dto.getId() == patron.getId()){
                ret = dto;
            }
        }
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
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
           //se enviaran al ExceptionManager
        }
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
    //ACTUALIZA EL ESTADO DE LAS LLAVES DEL INVENTARIO 
    public static void UpdateState() throws Exception{
            List<Key> keys = EntityController.ListKeys();
            Key key;
            Iterator iter = keys.iterator();
            while(iter.hasNext()){
                key = (Key) iter.next();
                if(key.getKeyState().getStateDescription().equals("Untradeable")){
                    if(isTradeable(key)){
                        key.setKeyState(EntityController.find(new KeyState("Tradeable")));
                        EntityController.Edit(key);
                    }   
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
        Iterator iter = keys.iterator();
        KeyDTO k;
        int cantidad = 0;
        while(iter.hasNext()){
            k = (KeyDTO) iter.next();
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
        //ZonedDateTime finaldate = inicial.withZoneSameInstant(ZoneId.systemDefault());
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
                        throw new Exception("is not a number");
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
    //LISTA LOS STEAMITEMS A PARTIR DE UNA MATRIZ DE PRECIOS //FALLA//NO USAR HASTA ARREGLAR
    private static List<SteamItem> ListSteamItems(List<Object[]> list) throws Exception {
        List<SteamItem> ret = new ArrayList();
        List<Object[]> list2 = list; 
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Object[] listelement = (Object[]) iter.next();
            int storeprice = numberConvertor(String.valueOf(listelement[1]));
            int sellprice = numberConvertor(String.valueOf(listelement[2]));
            int cant = 1;
            SteamItem item = new SteamItem();
            item.setCant(cant);
            item.setStoreprice(storeprice);
            item.setSellprice(sellprice);
            ret.add(item);
        }
        return ret;
    }
    //DEVUELVE EL VALOR DE GANANCIA DE UN TRADE
    private static int getGanancia(List<Object[]> list) throws Exception{
        int ganancia = 0;
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Object[] listelement = (Object[]) iter.next();
            int sellprice = numberConvertor(String.valueOf(listelement[2]));
            ganancia = ganancia + profit(sellprice);
        }
        return ganancia;
    }
    //CREA UN NUEVO TRADE EN BASE DE DATOS
    public static void EnterTrade(List<KeyDTO> keys, List<Object[]> items, int storeprice, int balance) throws Exception{
        List<Key> listakeys = new ArrayList();
        //SE CREA EL TRADE Y SE SETEAN LOS PARAMETROS
        int ganancia = getGanancia(items);
        Trade trade = new Trade();
        trade.setBalancestore(balance);
        trade.setGanancia(ganancia);
        trade.setPriceinstore(storeprice);
        trade.setDateoftrade(Calendar.getInstance().getTime());
        trade.setCantkey(keys.size());
        //SE PONEN LAS LLAVES COMO TRADED
        Iterator iter = keys.iterator();
        trade.setKeytraded(listakeys);
        EntityController.create(trade);
           while(iter.hasNext()){
             KeyDTO dto = (KeyDTO) iter.next();
             Key key = getKey(dto);
             key = ChangeState(key, "Traded");
             listakeys.add(key);
             EntityController.Edit(key);
        }
        //SE AGREGA LA GANANCIA AL SALDO
        SumarSaldo(ganancia);
        
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
//        List<TypeStateDTO> ret = new ArrayList();
//        Iterator iter = list.iterator();
//        while(iter.hasNext()){
//            TypeStateDTO dto = KeyManager.getStateType((String) iter.next());
//            if(dto != null){
//                    ret.add(dto);
//                }
//        }
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
    //PERMITE VENDER UNA KEY
    public static void SellKey(KeyDTO key, int sellprice) throws NonexistentEntityException, Exception{
        int ganancia = KeyManager.profit(sellprice);
        Key k = KeyManager.getKey(key);
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
    //DEVUELVE UNA LISTA CON SOLO LAS LLAVES TRADEABLES
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
    
    //DEVUELVE UNA LISTA DE LAS LLAVES PARA LA INTERFAZ
    public static List<KeyDTO> ListKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        Key key;
        List<KeyDTO> dtos = new ArrayList();
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
            KeyDTO dto = new KeyDTO(key.getKeyType().getTypeDescription(), key.getKeyState().getStateDescription());
            dto.setbuydate(key.getBuyDate());
            dto.setId(key.getId());
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA PARA MOSTRAR LOS TRADE
    public static List<Object[]> getTradeList() throws Exception{
        List<Object[]> ret = new ArrayList();
        List<Trade> trades = EntityController.ListTrades();
        Iterator iter = trades.iterator();
        while(iter.hasNext()){
            Trade trade = (Trade) iter.next();
            int keyprice = trade.getPriceinstore();
            int profit = trade.getGanancia();
            int cant = trade.getCantkey();
            int total = profit-(keyprice*cant);
            if(total<0){
                throw new Exception("getTradeListError");
            }else{
                double cantkey = (double) trade.getCantkey();
                double t = (double) total;
                double profitxkey = t/cantkey;
                profitxkey = profitxkey/100;
                String pk = String.valueOf(profitxkey);
                Object[] listelement = {trade.getId(),SimpleFormatDate(trade.getDateoftrade()), trade.getCantkey(),numberConvertor(numberConvertor(pk)) , numberConvertor(trade.getBalancestore()), numberConvertor(trade.getPriceinstore())};
                ret.add(listelement);
            }
            
        }
        return ret;
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
        DeleteUnUsedHistory();  
        List<History> last = EntityController.getLast(new History());
        if(last.isEmpty()){
            SaveHistory();
        }
        else if(last.get(0).getTotalmoney() != KeyManager.getTotalMoney()){
            SaveHistory();
        }
    }
    //BORRA LOS HISTORY REDUNDANTES
    private static void DeleteUnUsedHistory() throws NonexistentEntityException{
        List<History> hist = ListHistoryByType("Total");
        if(!hist.isEmpty()){
            for(History h : hist){
                if(HavePassed(h.getDate(),8)){
                    EntityController.destroy(h);
                }
            }
        }
        hist = ListHistoryByType("Balance");
        if(!hist.isEmpty()){
            for(History h : hist){
                if(HavePassed(h.getDate(),8)){
                    EntityController.destroy(h);
                }
            }
        }
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
    //BUSCA EL PARAMETRO INDICADO
    private static ParameterDTO findParameter(String name){
        SteamParameters sp = EntityController.find(new SteamParameters(name));
        ParameterDTO p = new ParameterDTO();
        p.setDescription(sp.getDescription());
        p.setName(sp.getName());
        p.setValue(sp.getValue());
        return p;
    }
    //LISTA LOS HISTORES PARA EL USO EN LA INTERFAZ
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
    public static List<Integer[]> ListHByType(String type){
        List<Integer[]> ret = new ArrayList();
        List<History> list = KeyManager.ListHistoryByType(type);
        if(!list.isEmpty()){
            list.forEach((h) -> {
                Integer[] ob = {getDayTimeValue(h.getDate()),h.getTotalmoney()};
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
        Key key;
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
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
      

      InitInventory();
      
      System.out.println("FIN");
      
  }
    
}
