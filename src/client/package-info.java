/**
 * <p>Contains JavaCommunicator client implementation.</p>
 * <p>The client GUI consists of two views:</p>
 * <ul>
 *     <li>Login view</li>
 *     <li>Conversation view</li>
 * </ul>
 *
 * Every view has a gui defined in fxml files. Appropriate controllers encapsulate logic for the view interactions.
 * <p>The client itself is a multithreaded java fx application with a non-blocking gui.</p>
 * <p>Clients connects to hardcoded localhost on port 4441</p>
 */
package client;