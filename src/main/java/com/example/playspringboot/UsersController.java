package com.example.playspringboot;

import java.util.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UsersController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	@ResponseBody
	ModelAndView index(ModelAndView mav) {
		System.out.println("show all");
		List<User> users = userRepository.findAll();
		mav.setViewName("index");
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView show(ModelAndView mav, @PathVariable String id) {
		System.out.println("show id:" + id);
		User user = userRepository.findById(id);
		mav.setViewName("show");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	ModelAndView newUser(ModelAndView mav) {
		System.out.println("show new");
		mav.setViewName("new");
		mav.addObject("user", new User());
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	ModelAndView create(@Valid User user, BindingResult bindingResult, ModelAndView mav) throws NoSuchAlgorithmException {
		// exclude password error
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			mav.setViewName("new");
			mav.addObject("validationError", "不正な値が入力されました。");
			mav.addObject("user", user);
			return mav;
		}
		// include pasword error
		if (!user.isValidPassword()) {
			//TODO set passwrod error message
			System.out.println(bindingResult.getAllErrors().toString());
			mav.setViewName("new");
			mav.addObject("validationError", "不正な値が入力されました。");
			mav.addObject("user", user);
			return mav;
		}
		if (!user.isConfirmed()) {
			mav.setViewName("new");
			mav.addObject("validationError", "入力されたパスワードが一致しません。");
			mav.addObject("user", user);
			return mav;
		}
		System.out.println(user);
		user.setCreatedBy(user.getName());
		user.setUpdatedBy(user.getName());
		System.out.println("create");
		user.setPasswordDigest(getDigest(user.getPassword()));
		userRepository.save(user);
		return new ModelAndView("redirect:/user/");
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView edit(@PathVariable String id, ModelAndView mav) {
		System.out.println("edit id:" + id);
		User user = userRepository.findById(id);
		mav.setViewName("edit");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	ModelAndView update(@Valid User user, BindingResult bindingResult, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			mav.setViewName("edit");
			mav.addObject("validationError", "不正な値が入力されました。");
			mav.addObject("user", user);
			return mav;
		}
		user.setUpdatedAt(new Date());
		user.setUpdatedBy(user.getName());
		System.out.println("update");
		userRepository.save(user);
		return new ModelAndView("redirect:/user/");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	String destroy(@PathVariable String id) {
		System.out.println("delete id:" + id + ";");
		userRepository.delete(id);
		return "redirect:/user/";
	}

	public String getDigest(String message) throws NoSuchAlgorithmException {
		byte[] bytes = MessageDigest.getInstance("MD5").digest(message.getBytes(StandardCharsets.UTF_8));
		return DatatypeConverter.printHexBinary(bytes);
	}
}