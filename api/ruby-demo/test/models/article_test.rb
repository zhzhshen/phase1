require 'test_helper'

class ArticleTest < ActiveSupport::TestCase
  test "should not save article without title" do
    article = Article.new
    assert_not article.save, "Saved the article without a title"
  end

  test "should save article with title" do
    article = Article.new({:title => "title"})
    assert article.save, "Saved the article with a title"
    assert_equal article.title, "title"
  end
end
