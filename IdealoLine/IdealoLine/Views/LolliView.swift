//
//  LolliView.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/5/22.
//

import SwiftUI
import Lottie

struct LolliView: UIViewRepresentable {
    typealias UIViewType = UIView
    
    func makeUIView(context: UIViewRepresentableContext<LolliView>) -> UIView {
        let view = UIView(frame: .zero)
        
        let animationView = AnimationView()
        let animation = Animation.named("loading-welcome-page")
        animationView.animation = animation
        animationView.contentMode = .scaleAspectFit
        animationView.loopMode = .loop
        animationView.play()
        
        animationView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(animationView)
        
        NSLayoutConstraint.activate([
            animationView.widthAnchor.constraint(equalTo: view.widthAnchor),
            animationView.heightAnchor.constraint(equalTo: view.heightAnchor)
        ])
        
        return view
    }
    
    func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<LolliView>) {}
}

struct LolliView_Previews: PreviewProvider {
    static var previews: some View {
        LolliView()
    }
}
