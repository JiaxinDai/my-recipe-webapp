package recipewebapp.controllers;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

       /**
     * Validate login credentials and redirect user to home page.
     * If user is successfully validated, redirect to home page.
     * Else redirect user with an Error Status
     * @param user
     * @param response
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/login")
    public String doubleSubmitLogin(@ModelAttribute User user, HttpServletResponse response, RedirectAttributes redirectAttributes){


        if(loginService.authenticateUser(user.getUsername(),user.getPassword()) ){

            Cookie cookieSessionId = new Cookie("sessionId", loginService.saveSessionData(user.getUsername()));
            Cookie cookieCSRF      = new Cookie("csrfCookie", loginService.generateRandomToken());

            response.addCookie(cookieSessionId);
            response.addCookie(cookieCSRF);

            return "redirect:/account";

        }else{

            redirectAttributes.addFlashAttribute("Status", "error");
            return "redirect:/";

        }
    }
}