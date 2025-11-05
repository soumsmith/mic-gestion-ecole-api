package com.vieecoles.steph;

//@ApplicationScoped
public class MyTransactionEventListeningBean {

//	@Inject
//	TransactionManager tx;

//	Gson g = new Gson();

//    void onBeginTransaction(@Observes @Initialized(TransactionScoped.class) Object event) {
//        // This gets invoked when a transaction begins.
//    	System.out.println(">>>>>>>>>>>>>>> TRANSACTION DEMARRÉE");
////    	System.out.println(g.toJson(event));
//
//    	try {
//			System.out.println("STATUT TX :::: "+tx.getTransaction().getStatus());
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }
//
//    void onBeforeEndTransaction(@Observes @BeforeDestroyed(TransactionScoped.class) Object event) {
//        // This gets invoked before a transaction ends (commit or rollback).
//    	System.out.println(">>>>>>>>>>>>>>> TRANSACTION AVANT ARRET");
////    	System.out.println(g.toJson(event));
//    	try {
//    		if(tx.getStatus() == Status.STATUS_COMMITTED) {
//    			System.out.println(">>>>>>>>>>>>>>> TRANSACTION COMMITED");
//    		}else if(tx.getStatus() == Status.STATUS_MARKED_ROLLBACK){
//    			System.out.println("-------------------------> TRANSACTION ROLLED BACK");
//    		} else {
//    			System.out.println("Voici le statut ::: "+tx.getStatus());
//    		}
////    		System.out.println("STATUT TX :::: "+tx.getTransaction().getStatus());
//			System.out.println(tx.getTransaction().toString());
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
//    void onAfterEndTransaction(@Observes @Destroyed(TransactionScoped.class) Object event) {
//        // This gets invoked after a transaction ends (commit or rollback).
//    	System.out.println(">>>>>>>>>>>>>>> TRANSACTION ARRETÉE");
////    	System.out.println(g.toJson(event));
//
//    	try {
//			System.out.println("STATUT TX :::: "+tx.getTransaction());
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
