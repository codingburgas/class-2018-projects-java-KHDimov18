package services;

import models.Customer;
import models.User;
import repositories.CustomerRepository;
import repositories.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public Boolean isLogin(String userName, String userPassword) {
        return userRepository.userAndPasswordMatch(userName, userPassword);
    }

    public User getLoggedInUser(String userName, String userPassword)
    {
        if(isLogin(userName, userPassword))
        {
            return userRepository.getUserByUserName(userName);
        }
        else
        {
            //if we try to get user object with wrong credentials
            return null;
        }
    }

    public Boolean addUser(String userName, String userPassword, String firstName, String lastName, String phoneNumber)
    {
        Customer customer = new Customer(0L, firstName, lastName, phoneNumber);
        Long customerId = customerRepository.addCustomer(customer);
        if(customerId!=-1L){
            User user = new User(customerId, userName, userPassword, false, customerId);
            return userRepository.addUser(user);
        }else{
            return false;
        }
    }

    public User getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> getAllUsers(){
        return userRepository.getUsers();
    }

    public Boolean updateUser(Long userId, String userName, String userPassword, Boolean isAdmin)
    {
        User user = getUserById(userId);
        if(user!=null)
        {
            user.setUserName(userName);
            user.setUserPassword(userPassword);
            user.setAdmin(isAdmin);

            return userRepository.updateUser(user, userId);

        } else {
            return false;
        }
    }

    public Boolean deleteUser(Long userId) {
        return userRepository.deleteUser(userId);
    }
}
