package com.delegation.horizon.controller

import com.delegation.horizon.model.User
import com.delegation.horizon.repository.MotorPredicateRepository
import com.delegation.horizon.repository.UserRepository
import com.delegation.horizon.request.MotorInsuranceDTO
import com.delegation.horizon.service.MotorInsuranceService
import com.delegation.horizon.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserController(
    val userRepository: UserRepository,
    val motorPredicateRepository: MotorPredicateRepository,
    val motorInsuranceService: MotorInsuranceService,
    val userService: UserService
) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @GetMapping("/users/{userId}")
    fun findUsersById(@PathVariable userId: String): User {
        return userService.findUsersById(userId)
    }


    @PutMapping("/users")
    fun updateUser(@RequestBody user: User): User {
        return userService.updateUser(user)
    }

    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: String): String {
        return userService.deleteUser(userId)

    }



}